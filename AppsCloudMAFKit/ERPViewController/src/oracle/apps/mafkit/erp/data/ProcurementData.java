/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

import java.util.ArrayList;
import java.util.List;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;
import oracle.apps.mafkit.erp.common.ErpUtils;

public class ProcurementData {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private ProviderChangeSupport _providerChangeSupport = new ProviderChangeSupport(this);
    private ErpUtils _utils = new ErpUtils();
    private long _id = 1;
    private long _reqNum = 8552477L;
    private Person _requestor;
    private List<Requisition> _recentRequisitions = new ArrayList<Requisition>();
    private List<ProductCategory> _topCategoriesList = new ArrayList<ProductCategory>();
    private List<String> _purchasingNews = new ArrayList<String>();
    private List<Product> _fullProductList = new ArrayList<Product>();
    private List<Product> _filteredProductList = new ArrayList<Product>();
    private List<Product> _shoppingCartList = new ArrayList<Product>();
    private int _shoppingCartItemCount = 0;
    private double _shoppingCartAmount = 0;
    
    public ProcurementData() {
        _buildRequestor();
        _buildRecentRequisitions();
        _buildTopProductCategoriesList();
        _buildPurchasingNewsList();
        _buildFullProductList();
    }//constructor

    public void addPropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.removePropertyChangeListener(l); }
    public void addProviderChangeListener(ProviderChangeListener l) { _providerChangeSupport.addProviderChangeListener(l); }
    public void removeProviderChangeListener(ProviderChangeListener l) { _providerChangeSupport.removeProviderChangeListener(l); }
    
    //Accessors
    public void setRequestor(Person p) { _requestor = p; }
    public Person getRequestor() { return _requestor; }
    public void setRecentRequisitions(List<Requisition> l) { 
        List<Requisition> oldValue = _recentRequisitions;
        _recentRequisitions = l; 
        _propertyChangeSupport.firePropertyChange("recentRequisitions", oldValue, _recentRequisitions);
    }//setRecentRequisitions
    public List<Requisition> getRecentRequisitions() { return _recentRequisitions; }
    public void setTopCategoriesList(List<ProductCategory> l) { _topCategoriesList = l; }
    public List<ProductCategory> getTopCategoriesList() { return _topCategoriesList; }
    public void setPurchasingNews(List<String> l) { _purchasingNews = l; }
    public List<String> getPurchasingNews() { return _purchasingNews; }
    public void setFullProductList(List<Product> l) { _fullProductList = l; }
    public List<Product> getFullProductList() { return _fullProductList; }
    public void setFilteredProductList(List<Product> l) {
        List<Product> oldFilteredProductList = _filteredProductList;
        _filteredProductList = l;
        _propertyChangeSupport.firePropertyChange("filteredProductList", oldFilteredProductList, _filteredProductList);
    }//setFilteredProductList
    public List<Product> getFilteredProductList() { return _filteredProductList; }
    public void setShoppingCartList(List<Product> l) { 
        List<Product> oldShoppingCartList = _shoppingCartList;
        _shoppingCartList = l; 
        _propertyChangeSupport.firePropertyChange("shoppingCartList", oldShoppingCartList, _shoppingCartList);
    }//setShoppingCartList
    public List<Product> getShoppingCartList() { return _shoppingCartList; }
    public void setShoppingCartItemCount(int i) {
        int oldValue = _shoppingCartItemCount;
        _shoppingCartItemCount = i;
        _propertyChangeSupport.firePropertyChange("shoppingCartItemCount", oldValue, _shoppingCartItemCount);
    }//setShoppingCartItemCount
    public int getShoppingCartItemCount() { return _shoppingCartItemCount; }
    public void setShoppingCartAmount(double d) {
        double oldValue = _shoppingCartAmount;
        _shoppingCartAmount = d;
        _propertyChangeSupport.firePropertyChange("shoppingCartAmount", oldValue, _shoppingCartAmount);
    }//setShoppingCartAmount
    public double getShoppingCartAmount() { return _shoppingCartAmount; }

    //Invoked from value change listener on search field
    public void filterProductList(String searchString){
        List<Product> newProductList = new ArrayList<Product>();
        if (searchString != null && searchString.length() > 0){
            //Loop through full product list
            for (Product product : _fullProductList){
                //Look for a pattern match in category, name and description
                if (product.getCategory().toUpperCase().contains(searchString.toUpperCase()) ||
                    product.getName().toUpperCase().contains(searchString.toUpperCase()) ||
                    product.getDescription().toUpperCase().contains(searchString.toUpperCase())){
                    newProductList.add(product);
                }//pattern match
            }//loop
        } else {
            for (Product product : _fullProductList)
                newProductList.add(product);
        }//pattern supplied check
        setFilteredProductList(newProductList);
        _providerChangeSupport.fireProviderRefresh("filteredProductList");
    }//filterProductList
    
    //Invoked from add to cart button
    public void addToCart(long productId, int quantity){
        for (Product p : getFilteredProductList()){
            if (p.getId() == productId){
                p.setQuantity(quantity);
                _shoppingCartList.add(p);
                break;
            }//check id
        }//loop through product list
        setShoppingCartItemCount(_shoppingCartList.size());
        calculateCartAmount();
    }//addToCart
    
    //Invoked from remove from cart button
    public void removeFromCart(long productId){
        List<Product> productList = getShoppingCartList();        
        for (Product p : productList){
            if (p.getId() == productId){
                p.setQuantity(0);
                productList.remove(p);
                break;
            }//check id
        }//loop through product list
        setShoppingCartList(productList);
        setShoppingCartItemCount(_shoppingCartList.size());
        calculateCartAmount();
        _providerChangeSupport.fireProviderRefresh("shoppingCartList");
    }//removeFromCart
    
    public void calculateCartAmount(){
        double cartAmount = 0;
        for (Product p : _shoppingCartList)
            cartAmount += p.getPrice();
        setShoppingCartAmount(cartAmount);
    }//calculateCartAmount
    
    //Invoked from submit button on cart page
    public void submitCartForApproval() {
        //Create a new recent requisition
        List<Requisition> requisitions = getRecentRequisitions();
        List<RequisitionLine> requisitionLines = new ArrayList<RequisitionLine>();
        for (Product p : _shoppingCartList) {
            requisitionLines.add(new RequisitionLine(p, p.getQuantity()));
        }//loop
        Requisition requisition = new Requisition("US-" + _reqNum++, _utils.addDaysToToday(0), requisitionLines, 0, _requestor);
        requisitions.add(0, requisition);
        setRecentRequisitions(requisitions);
        //Clear shopping cart
        setShoppingCartList(new ArrayList<Product>());
        setShoppingCartItemCount(0);
        setShoppingCartAmount(0);
    }//submitCartForApproval

    //******************************************************************************************************************
    // Helpers
    //******************************************************************************************************************
    
    private void _buildRequestor() {
        _requestor = new Person(_id++, "Lisa Jones", "/resources/images/headshot04.png", "Lisa.Jones@example.com", 
                                "+1(650)506-7000", "999 Oracle Parkway", "M/S 9op999", "Redwood Shores", "CA", "94065");
    }//_buildRequestor

    private void _buildRecentRequisitions() {
        Requisition requisition;
        List<RequisitionLine> requisitionLines;
        Product product;
        List<PriceBreak> priceBreakList;
        //Recent-Requisition-1
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 26.25, 29.25));
        priceBreakList.add(new PriceBreak(20, 21.25, 29.25));
        product = new Product(_id++, "Logitech HD Webcam C310", "Logitech HD Webcam C310", "WebCams", 
                              "/resources/images/LogitechHDWebcamC310.jpg", "Pacific Supplies Inc.",
                              "PSI-WC-191", "Logitech", "C310310", 29.25, priceBreakList);
        requisitionLines = new ArrayList<RequisitionLine>();
        requisitionLines.add(new RequisitionLine(product,2));
        requisition = new Requisition("US-" + _reqNum++, _utils.addDaysToToday(-5), requisitionLines, 0, _requestor);
        requisition.setStatus("Approved");
        _recentRequisitions.add(requisition);
        //Recent-Requisition-2
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(20, 10.25, 12.75));
        priceBreakList.add(new PriceBreak(50, 8.05, 12.75));
        product = new Product(_id++, "OOW CFS Supplies", "OOW CFS Supplies", "Supplies", 
                              "/resources/images/CFSSupplies.jpg", "Comet Supplies Inc.",
                              "CSI-OOW-16", "CFS Manufaturing", "C161616", 12.75, priceBreakList);
        requisitionLines = new ArrayList<RequisitionLine>();
        requisitionLines.add(new RequisitionLine(product,1));
        requisition = new Requisition("US-" + _reqNum++, _utils.addDaysToToday(-10), requisitionLines, 0, _requestor);
        requisition.setStatus("Approved");
        _recentRequisitions.add(requisition);
        //Recent-Requisition-3
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 35.25, 39.59));
        priceBreakList.add(new PriceBreak(10, 30.05, 39.59));
        product = new Product(_id++, "15.6-Inch Laptop and Tablet Bag", "15.6-Inch Laptop and Tablet Bag", "Supplies", 
                              "/resources/images/LaptopBag.jpg", "Comet Supplies Inc.",
                              "CSI-LB-20", "Stylish Manufaturing", "S202020", 39.59, priceBreakList);
        requisitionLines = new ArrayList<RequisitionLine>();
        requisitionLines.add(new RequisitionLine(product,1));
        requisition = new Requisition("US-" + _reqNum++, _utils.addDaysToToday(-15), requisitionLines, 0, _requestor);
        requisition.setStatus("Approved");
        _recentRequisitions.add(requisition);
    }//_buildRecentRequisitions

    private void _buildTopProductCategoriesList() {
        _topCategoriesList.add(new ProductCategory("Laptops", "/resources/images/Laptops.jpg"));
        _topCategoriesList.add(new ProductCategory("Cell Phones", "/resources/images/CellPhones.jpg"));
        _topCategoriesList.add(new ProductCategory("Mice", "/resources/images/Mice.jpg"));
        _topCategoriesList.add(new ProductCategory("Batteries", "/resources/images/Batteries.jpg"));
        _topCategoriesList.add(new ProductCategory("Headsets", "/resources/images/Headsets.jpg"));
    }//_buildTopProductCategoriesList
    
    private void _buildPurchasingNewsList() {
        _purchasingNews.add("The Apple iPhone 7 is now available for employees to upgrade");
        _purchasingNews.add("Order customer gifts before Dec. 9 to ensure delivery before Christmas");
    }//_buildPurchasingNewsList
    
    private void _buildFullProductList(){
        List<PriceBreak> priceBreakList;
        Product product;
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 35.25, 39.59));
        priceBreakList.add(new PriceBreak(10, 30.05, 39.59));
        product = new Product(_id++, "15.6-Inch Laptop and Tablet Bag", "15.6-Inch Laptop and Tablet Bag", "Supplies", 
                              "/resources/images/LaptopBag.jpg", "Comet Supplies Inc.",
                              "CSI-LB-20", "Stylish Manufaturing", "S202020", 39.59, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 105.25, 112.50));
        priceBreakList.add(new PriceBreak(10, 98.05, 112.50));
        product = new Product(_id++, "Plantronics Bluetooth Headphone for Apple and Samsung phones", 
                              "Compatible with Apple iPhone 7,6s,6,5s,5c,5, Samsung Galaxy S7,S6,S5,S4,S3", 
                              "Headsets", "/resources/images/headset1.jpg", "Example Supplies Inc.", "PRC-RWS-H0101", 
                              "Plantronics", "P192837", 112.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 115.25, 122.50));
        priceBreakList.add(new PriceBreak(10, 108.05, 122.50));
        product = new Product(_id++, "Sennheiser UC Wireless Bluetooth Headset Presence-UC", 
                              "Compact and Lightweight headset designed for mobile", 
                              "Headsets", "/resources/images/headset2.jpg", "Example Supplies Inc.", "PRC-RWS-H0202", 
                              "Sennheiser", "S192837", 122.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 125.25, 132.50));
        priceBreakList.add(new PriceBreak(10, 118.05, 132.50));
        product = new Product(_id++, "Plantronics CS540 Office Wireless Headset with Handset Lifter", 
                              "Compatible with Apple iPhone 7,6s,6,5s,5c,5, Samsung Galaxy S7,S6,S5,S4,S3", 
                              "Headsets", "/resources/images/headset3.jpg", "Example Supplies Inc.", "PRC-RWS-H0303", 
                              "Plantronics", "P192837", 132.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 105.25, 112.50));
        priceBreakList.add(new PriceBreak(10, 98.05, 112.50));
        product = new Product(_id++, "Sennheiser VersaMate Convertible Corded Office Telephone Headset", 
                              "Compact and Lightweight headset designed for mobile", 
                              "Headsets", "/resources/images/headset4.jpg", "Example Supplies Inc.", "PRC-RWS-H0404", 
                              "Sennheiser", "S192837", 112.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 115.25, 122.50));
        priceBreakList.add(new PriceBreak(10, 108.05, 122.50));
        product = new Product(_id++, "NOA Bluetooth 4.1 Wireless Stereo Headsets Neckband with Retractable Bluetooth Earbuds", 
                              "Compatible with Apple iPhone 7,6s,6,5s,5c,5, Samsung Galaxy S7,S6,S5,S4,S3", 
                              "Headsets", "/resources/images/headset5.jpg", "Example Supplies Inc.", "PRC-RWS-H0505", 
                              "NOA", "N192837", 122.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 105.25, 112.50));
        priceBreakList.add(new PriceBreak(10, 98.05, 112.50));
        product = new Product(_id++, "Sennheiser PC 151 Binaural Headset with Noise-Canceling Microphone & Volume Control", 
                              "Compatible with Apple iPhone 7,6s,6,5s,5c,5, Samsung Galaxy S7,S6,S5,S4,S3", 
                              "Headsets", "/resources/images/headset6.jpg", "Example Supplies Inc.", "PRC-RWS-H0606", 
                              "Sennheiser", "S192837", 112.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 105.25, 112.50));
        priceBreakList.add(new PriceBreak(10, 98.05, 112.50));
        product = new Product(_id++, "Plantronics Voyager Legend Wireless Bluetooth Headset", 
                              "Compact and Lightweight headset designed for mobile", 
                              "Headsets", "/resources/images/headset7.jpg", "Example Supplies Inc.", "PRC-RWS-H0707", 
                              "Plantronics", "P192837", 112.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 105.25, 112.50));
        priceBreakList.add(new PriceBreak(10, 98.05,112.50));
        product = new Product(_id++, "Plantronics M165 Marque 2 Ultralight Wireless Bluetooth Headset", 
                              "Compact and Lightweight headset designed for mobile", 
                              "Headsets", "/resources/images/headset8.jpg", "Example Supplies Inc.", "PRC-RWS-H0808", 
                              "Jabra", "J192837", 112.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 205.25, 212.50));
        priceBreakList.add(new PriceBreak(10, 198.05, 212.50));
        product = new Product(_id++, "Jabra Headset Binaural with Noise Canceling Boom", 
                              "Compact and Lightweight headset designed for mobile", 
                              "Headsets", "/resources/images/headset9.jpg", "Example Supplies Inc.", "PRC-RWS-H0909", 
                              "Jabra", "J192837", 212.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(5, 105.25, 112.50));
        priceBreakList.add(new PriceBreak(10, 98.05, 112.50));
        product = new Product(_id++, "Zoom Telephonics ZoomSwitch Headset Accessory", 
                              "Compact and Lightweight headset designed for mobile", 
                              "Headsets", "/resources/images/headset10.jpg", "Example Supplies Inc.", "PRC-RWS-H1010", 
                              "Zoom", "Z192837", 112.50, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 26.25, 29.25));
        priceBreakList.add(new PriceBreak(20, 21.25, 29.25));
        product = new Product(_id++, "Logitech HD Webcam C310", "Logitech HD Webcam C310", "WebCams", 
                              "/resources/images/LogitechHDWebcamC310.jpg", "Pacific Supplies Inc.",
                              "PSI-WC-191", "Logitech", "C310310", 29.25, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "Plantronics M71 Ultralight Wireless Mouse Trackpad", "Combination device", 
                              "Supplies", "/resources/images/mouse1.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "Plantronics", "P161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "TeckNet PURE 2.4G Mini Wireless Mouse", "18 Month Battery Life, 3 DPI Levels", 
                              "Supplies", "/resources/images/mouse2.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "TeckNet", "T161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "MAKIYO Ultra Thin USB Optical Wireless Mouse", "2.4G Receiver Super Slim", 
                              "Supplies", "/resources/images/mouse3.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "MAKIYO", "M161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "Microsoft Wireless Mobile Mouse 4000", "Compact and Lightweight in Graphite", 
                              "Supplies", "/resources/images/mouse4.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "MicrosoftX", "M161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "Microsoft Arc Touch Mouse (Black)", "Microsoft Arc Touch Mouse (Black)", 
                              "Supplies", "/resources/images/mouse5.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "Microsoft", "M161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "Nano 2.4G Wireless Optical Mouse", " With DPI Switch (Black)", 
                              "Supplies", "/resources/images/mouse6.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "Nano", "N161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "JETech 2.4Ghz Wireless Mobile Optical Mouse", "3 DP Level with 6 Buttons", 
                              "Supplies", "/resources/images/mouse7.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "JETech", "JETech161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "TeckNet 2.4G Nano Wireless Mouse", "Compact with 5 buttons", 
                              "Supplies", "/resources/images/mouse8.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "T", "T161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "Microsoft 2.4ghz Wireless Foldable Optical Mouse", "Compact Folding Arc", 
                              "Supplies", "/resources/images/mouse9.jpg", "Example Supplies Inc.", "PRC-RWS-111", 
                              "Microsoft", "M161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(10, 19.25, 22.75));
        priceBreakList.add(new PriceBreak(20, 15.25, 22.75));
        product = new Product(_id++, "Logitech Wireless Mouse M325", "Designed for web scrolling", 
                              "Supplies", "/resources/images/mouse10.jpg", "Example Supplies Inc.", "PRC-RWS-1101", 
                              "Logitech", "L161616", 22.75, priceBreakList);
        _fullProductList.add(product);
        //
        priceBreakList = new ArrayList<PriceBreak>();
        priceBreakList.add(new PriceBreak(20, 10.25, 12.75));
        priceBreakList.add(new PriceBreak(50, 8.05, 12.75));
        product = new Product(_id++, "OOW CFS Supplies", "OOW CFS Supplies", "Supplies", 
                              "/resources/images/CFSSupplies.jpg", "Comet Supplies Inc.",
                              "CSI-OOW-16", "CFS Manufaturing", "C161616", 12.75, priceBreakList);
        _fullProductList.add(product);
    }//_buildFullProductList
    
}//ProcurementData
