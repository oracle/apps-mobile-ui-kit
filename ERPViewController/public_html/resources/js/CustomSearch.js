/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
(function ()
{
  var timeout;
  var searchActivated = false;
  var minlength = 1;
  var clearFiresAction = false;
  var showErrorForFewChars = false;

  try 
  {

    // TypeHandler for custom "csearch" tags:
    var csearch = adf.mf.api.amx.TypeHandler.register("http://xmlns.example.com/custom", "csearch");

    /**
     * This method renders the search component
     */
    csearch.prototype.render = function (amxNode, id)
    {
      var rootElement;
      var myType = amx.getTextValue(amxNode.getAttribute("controlType"));
      if (myType == "search")
      {
        rootElement = document.createElement("form");
      }
      else 
      {
        rootElement = document.createElement("div");
      }
      var subElement = document.createElement("div");

      try 
      {
        var width = amx.getTextValue(amxNode.getAttribute("width"));
        rootElement.style.width = width;

        rootElement.className = "csearch";

        var tempClearFires = amx.getTextValue(amxNode.getAttribute("clearFiresAction"));
        if (tempClearFires.length > 0)
        {
          clearFiresAction = true;
        }

        var styleClass = amx.getTextValue(amxNode.getAttribute("styleClass"));

        var tempLength = amx.getTextValue(amxNode.getAttribute("minLength"));
        if (tempLength.length > 0)
        {
          minlength = parseInt(tempLength);
        }

        var labelText = amx.getTextValue(amxNode.getAttribute("label"));
        if (labelText.length > 0)
        {
          var labelSubElement = document.createElement("div");
          var labelElement = document.createElement("label");
          labelElement.innerHTML = labelText;
          labelSubElement.setAttribute("class", styleClass + "_label");
          labelSubElement.appendChild(labelElement);
          rootElement.appendChild(labelSubElement);
        }

        var hintText = amx.getTextValue(amxNode.getAttribute("hintText"));
        var inputElement = document.createElement("input");

        searchActivated = false;

        inputElement.value = amx.getTextValue(amxNode.getAttribute("value"));

        inputElement.id = id + "inputSearchElement";
        inputElement.setAttribute("type", "text");

        if (styleClass.length > 0)
        {
          inputElement.setAttribute("class", styleClass + "_text_input");
          subElement.setAttribute("class", styleClass + "_text");
        }
        else 
        {
          inputElement.setAttribute("class", "search rounded");
        }
        inputElement.setAttribute("placeholder", hintText);
        inputElement.setAttribute("role", "textbox");
        inputElement.setAttribute("autocomplete", "off");
        inputElement.setAttribute("autocorrect", "off");
        inputElement.setAttribute("autocapitalize", "on");

        if (myType == "search")
        {
          inputElement.setAttribute("name", "search");
          inputElement.setAttribute("type", "search");
        }
        if (myType == "url")
        {
          inputElement.setAttribute("type", "url");
          inputElement.setAttribute("autocapitalize", "off");
        }
        if (myType == "number")
        {
          inputElement.setAttribute("type", "number");
          inputElement.setAttribute("autocapitalize", "off");
        }

        showErrorForFewChars = (amx.getTextValue(amxNode.getAttribute("showErrorForFewChars")) == 'true');

        subElement.appendChild(inputElement);

        var anchorElement = document.createElement("a");
        anchorElement.id = id + "anchor";
        anchorElement.setAttribute("href", "#");

        subElement.appendChild(anchorElement);
        rootElement.appendChild(subElement);

        //        anchorElement.setAttribute("class", "clear_button");
        //        if (clearFiresAction)
        //        {
        if (typeof inputElement.value !== "undefined" && inputElement.value !== null && inputElement.value.length > 0)
        {
          anchorElement.setAttribute("class", "clear_button_visible");
        }
        else 
        {
          inputElement.style.backgroundPositionX = "calc(50% - 8px - " + Math.ceil(((typeof inputElement.placeholder !== "undefined" && inputElement.placeholder !== null) ? inputElement.placeholder.length : 0) * 0.85 / 2) + "ch)";
          anchorElement.setAttribute("class", "clear_button");
          setTimeout(function ()
          {
            //To keep the focus of the search field when entering the search page
            if (amx.getTextValue(amxNode.getAttribute("setFocus")) == 'true')
            {
              inputElement.focus();
            }
          },
          5);
        }
        //        }
        var eventData = 
        {
          "inputElement.id" : inputElement.id, "anchorElement.id" : anchorElement.id, "amxNode" : amxNode, "context" : this
        };

        adf.mf.api.amx.addBubbleEventListener(rootElement, "submit", function ()
        {
          var inputElementValue = inputElement.value;
          if (inputElementValue != null && inputElementValue.length >= minlength)
          {
            searchActivated = true;
            adf.mf.el.setValue(
          {
            "name" : "#{pageFlowScope.inPicker}", "value" : "y"
          },
          function onSuccess(request, response)
          {
            // Process any return values that comes back in the "response" parameter 
          },
          function onFail(request, response)
          {
          });
          
            inputElement.blur();
          }
          else if (showErrorForFewChars && minlength > 0)
          {
            var errorPopupOpenerForFewChars = document.getElementById(amx.getTextValue(amxNode.getAttribute("errorButtonIdForFewChars")));
            if (errorPopupOpenerForFewChars != null && errorPopupOpenerForFewChars != undefined)
            {
              inputElement.blur();
              adf.mf.el.setValue(
              {
                "name" : "#{viewScope.fewCharactersError_message}", "value" : "A minimum of " + minlength + " character" + (minlength > 1 ? "s" : "") + " is required for search."
              },
              function onSuccess(request, response)
              {
              },
              function onFail(request, response)
              {
                adf.mf.log.Application.logp(adf.mf.log.level.WARNING, "CustomSearch", "showErrorPopup", "Failed to set error message.");
              });
              adf.mf.api.amx.triggerBubbleEventListener(errorPopupOpenerForFewChars, "tap");
            }
            else 
            {
              adf.mf.log.Application.logp(adf.mf.log.level.WARNING, "CustomSearch", "showErrorPopup", "Hidden button to launch error popup not found.");
            }
          }
          //this._handleBlur(event);
          return false;
        });

        adf.mf.api.amx.addBubbleEventListener(inputElement, "keyup", this._handleTextChange, eventData);

        adf.mf.api.amx.addBubbleEventListener(anchorElement, "tap", this._clearText, eventData);

        adf.mf.api.amx.addBubbleEventListener(inputElement, "blur", this._handleBlur, eventData);

        adf.mf.api.amx.addBubbleEventListener(inputElement, "focus", function ()
        {
          searchActivated = false;
          if (typeof inputElement.value !== "undefined" && inputElement.value !== null && inputElement.value.length < 1)
          {
            inputElement.style.backgroundPositionX = "";
          }
          
           adf.mf.el.setValue(
          {
            "name" : "#{pageFlowScope.blur}", "value" : "y"
          },
          function onSuccess(request, response)
          {
            // Process any return values that comes back in the "response" parameter 
          },
          function onFail(request, response)
          {
          });
        },
        eventData);

        var advancedButton = document.getElementById('cb1');
        adf.mf.api.amx.addBubbleEventListener(advancedButton, "touchend", this._handFocusOnButton);
      }
      catch (problem)
      {
        adf.mf.log.Framework.logp(adf.mf.log.level.SEVERE, "example", "render", "Problem with custom component creation: " + problem);
      }
      return rootElement;
    };

    csearch.prototype.createChildrenNodes = function (amxNode)
    {
      // Call the register input value during node creation as it requires the EL context
      // to be setup and rendering is not performed in EL context (expects all EL to already
      // be resolved during rendering)
      amx.registerInputValue(amxNode, "value");

      // Return false to let the framework create the children
      return false;
    };
    csearch.prototype._handFocusOnButton = function ()
    {
    };
    csearch.prototype._handleBlur = function (event)
    {
    
     adf.mf.el.setValue(
          {
            "name" : "#{pageFlowScope.blur}", "value" : "n"
          },
          function onSuccess(request, response)
          {
            // Process any return values that comes back in the "response" parameter 
          },
          function onFail(request, response)
          {
          });
          
       
      var anchorElementId = event.data["anchorElement.id"];
      var anchorElement = document.getElementById(anchorElementId);
      var inputElementId = event.data["inputElement.id"];
      var inputElement = document.getElementById(inputElementId);
      var inputElementValue = inputElement.value;
      
      if (typeof inputElementValue !== "undefined" && inputElementValue !== null && inputElementValue.length > 0)
      {
        inputElement.style.backgroundPositionX = "";
      }
      else 
      {
        inputElement.style.backgroundPositionX = "calc(50% - 8px - " + Math.ceil(((typeof inputElement.placeholder !== "undefined" && inputElement.placeholder !== null) ? inputElement.placeholder.length : 0) * 0.85 / 2) + "ch)";
      }
      //      if (!clearFiresAction)
      //      {
      //        anchorElement.className = "clear_button";
      //      }
      if (searchActivated && adf.mf.api.amx.acceptEvent())
      {
        var amxNode = event.data["amxNode"];

        var actionEvent = new amx.ActionEvent();
        var actionListener = amxNode.getAttributeExpression("actionListener");
        if (actionListener != null)
        {
          adf.mf.el.setValue(
          {
            "name" : "#{pageFlowScope.hasSearched}", "value" : "y"
          },
          function onSuccess(request, response)
          {
            // Process any return values that comes back in the "response" parameter 
          },
          function onFail(request, response)
          {
          });

          adf.mf.el.setValue(
          {
            "name" : "#{pageFlowScope.searchSucceeded}", "value" : "y"
          },
          function onSuccess(request, response)
          {
            // Process any return values that comes back in the "response" parameter 
          },
          function onFail(request, response)
          {
          });
        }

        adf.mf.api.amx.processAmxEvent(amxNode, "action", undefined, undefined, actionEvent, function ()
        {
          // use an empty function here if you don't want to involve navigation
          var action = amxNode.getAttributeExpression("action", true, true);
          if (action != null)
          {
            console.log("Setting pageFlowScope and firing search within submit");
            adf.mf.el.setValue(
            {
              "name" : "#{pageFlowScope.hasSearched}", "value" : "y"
            },
            function onSuccess(request, response)
            {
              // Process any return values that comes back in the "response" parameter 
            },
            function onFail(request, response)
            {
            });
            adf.mf.el.setValue(
            {
              "name" : "#{pageFlowScope.searchSucceeded}", "value" : "y"
            },
            function onSuccess(request, response)
            {
              // Process any return values that comes back in the "response" parameter 
            },
            function onFail(request, response)
            {
            });
            adf.mf.api.amx.doNavigation(action);
          }
        });
      }

    }

    /**
     * This method is called by the framework while executing the _handleText method to refresh the
     * search component without rerendering it
     */
    csearch.prototype.updateChildren = function (amxNode, attributeChanges)
    {
      if (attributeChanges.hasChanged("value"))
      {
        return adf.mf.api.amx.AmxNodeChangeResult["REFRESH"];
      }
      return adf.mf.api.amx.AmxNodeChangeResult["RERENDER"];
    };

    /**
     * This method is called when the updateChildren method returns a refresh
     * result.
     */
    csearch.prototype.refresh = function (amxNode, attributeChanges)
    {
      if (attributeChanges.hasChanged("value"))
      {
        var inputField = document.getElementById(amxNode.getId() + "inputSearchElement");
        var curPos = getCaretPosition(inputField);
        var newValue = amxNode.getAttribute("value");
        inputField.value = newValue;
        setCaretPosition(inputField,curPos);
      }
    };

    /**
     * This method is invoked on keyup event (while entering the text within the search component).
     */
    csearch.prototype._handleTextChange = function (event, forced)
    {
      var inputElementId = event.data["inputElement.id"];
      var amxNode = event.data["amxNode"];
      var anchorElementId = event.data["anchorElement.id"];
      var anchorElement = document.getElementById(anchorElementId);
      var inputElement = document.getElementById(inputElementId);
      var inputElementValue = inputElement.value;

      if (inputElementValue != null && inputElementValue.length > 0)
      {
        anchorElement.className = "clear_button_visible";
      }
      else 
      {
        anchorElement.className = "clear_button";
      }

      var oldInputElementValue = null;
      var vs = amxNode.getVolatileState();
      if (vs != null)
        oldInputElementValue = ["oldInputElementValue"];

      if (oldInputElementValue != inputElementValue || forced)
      {
        amxNode.setVolatileState(
        {
          "oldInputElementValue" : inputElementValue
        });
        inputElement.setAttribute("value", inputElementValue);

        var vce = new adf.mf.api.amx.ValueChangeEvent(oldInputElementValue, inputElementValue);
        var success = function ()
        {
        };// do nothing
        adf.mf.api.amx.processAmxEvent(amxNode, "valueChange", "value", inputElementValue, vce, success);

        /*
        if (timeout)
        {
          clearTimeout(timeout);
        }

        // Start new timeout
        timeout = setTimeout(function()
          {
            var vce = new adf.mf.api.amx.ValueChangeEvent(oldInputElementValue, inputElementValue);
            var success = function() {}; // do nothing
            adf.mf.api.amx.processAmxEvent(
              amxNode,
              "valueChange",
              "value",
              inputElementValue,
              vce,
              success);
          },
          400);
          */
        adf.mf.el.setValue(
        {
          "name" : "#{pageFlowScope.searchSucceeded}", "value" : "n"
        },
        function onSuccess(request, response)
        {
          // Process any return values that comes back in the "response" parameter 
        },
        function onFail(request, response)
        {
        });
      }

    };

    /**
     * This method clears the text within the search input text compoent on
     * tapping the X icon.
     */
    csearch.prototype._clearText = function (event)
    {
      var inputElementId = event.data["inputElement.id"];
      var inputElement = document.getElementById(inputElementId);
      inputElement.value = "";
      var anchorElementId = event.data["anchorElement.id"];
      var anchorElement = document.getElementById(anchorElementId);
      anchorElement.className = "clear_button";
      var context = event.data["context"];
      context._handleTextChange(event, true);

      if (clearFiresAction)
      {
        var amxNode = event.data["amxNode"];

        var actionEvent = new amx.ActionEvent();
        adf.mf.api.amx.processAmxEvent(amxNode, "action", undefined, undefined, actionEvent, function ()
        {
          // use an empty function here if you don't want to involve navigation
          var action = amxNode.getAttributeExpression("action", true, true);
          if (action != null)
          {
            adf.mf.api.amx.doNavigation(action);
          }
        });
      }
      setTimeout(function ()
      {
        //To keep the focus of the search field after the clear icon is clicked
        inputElement.focus();
      },
      5);
    };
  }
  catch (problem)
  {
    adf.mf.log.Framework.logp(adf.mf.log.level.SEVERE, "Custom.js", "*", "Problem with custom code: " + problem);
  }

  //register  
  setFocusOnCustomSearchComponent = function ()
  {
    var rootElement = document.querySelectorAll('[id^="customSearchBox"]')[0];
    if (rootElement != null && rootElement != undefined)
    {
      var inputElement = rootElement.getElementsByTagName('input')[0];
      inputElement.focus();
    }
  };
  
  function getCaretPosition (elem) {
    var caretPos = 0;
    if (document.selection) {
      var oSel = document.selection.createRange();
      oSel.moveStart('character', -elem.value.length);
      caretPos = oSel.text.length;
    }
    else if (elem.selectionStart || elem.selectionStart == '0')
      caretPos = elem.selectionStart;
      return caretPos;
  }
  
  function setCaretPosition(elem, caretPos) {
    if(elem != null) {
        if(elem.createTextRange) {
            var range = elem.createTextRange();
            range.move('character', caretPos);
            range.select();
        }
        else {
            if(elem.selectionStart) {
                elem.focus();
                elem.setSelectionRange(caretPos, caretPos);
            }
            else
                elem.focus();
        }
    }
  }

})();