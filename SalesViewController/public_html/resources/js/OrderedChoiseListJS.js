/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
(function () {
    handleOrderedChoiceListScroll = function () {
        var oreredChoiceListComponents = document.getElementsByClassName('EMA_orderedChoiceList');
        var oreredChoiceListComponentsReadOnly = document.getElementsByClassName('EMA_orderedChoiceList-readOnly');
 setTimeout(function () {
        scrollEveryComponent(oreredChoiceListComponents);
        scrollEveryComponent(oreredChoiceListComponentsReadOnly);
         }, 500);
}

handleOrderedChoiceListOnSelection = function () {
    var oreredChoiceListComponents = document.getElementsByClassName('EMA_orderedChoiceList');
    setTimeout(function () {
        scrollEveryComponent(oreredChoiceListComponents);
    }, 200);
}

    function getDeviceWidth() {
        var el = "#{deviceScope.hardware.screen.availableWidth}";
        var returnValue = 300;
        adf.mf.el.getValue(el, function (req, res) {
            try {
                returnValue = res[0].value;
            }
            catch (problem) {
                console.log('problem in fetching device width' + problem)
            }
        },
        function noOp() {
        });
        return returnValue;
    }
    
    function scrollEveryComponent(oreredChoiceListComponents){
        for (i = 0;i < oreredChoiceListComponents.length;i++) {
            var currentComponent = oreredChoiceListComponents[i].getElementsByClassName('EMA_orderedChoiceList-currentState')[0];
            var deviceWidth = getDeviceWidth();
            var currentComponentWidth = currentComponent.offsetWidth;
            var scrollPosition = currentComponent.offsetLeft + (currentComponentWidth / 2) - (deviceWidth / 2) +15;
            //oreredChoiceListComponents[i].scrollLeft = scrollPosition;
            //:TODO - check for RTL
            $('#'+oreredChoiceListComponents[i].id.replace(/:/g, '\\:')).animate( {
                    scrollLeft : scrollPosition
                },
                500);
        }
    }

})();