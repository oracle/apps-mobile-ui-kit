(function () {
    showConfirmationPopup = function () {
        var element = document.getElementById("cb101");
        customTriggerEvent(element, "touchstart");
        customTriggerEvent(element, "touchend");
    }

    closeConfirmationPopup = function () {
        var element = document.getElementById("cb102");
        customTriggerEvent(element, "touchstart");
        customTriggerEvent(element, "touchend");
    }

    navigate = function () {
        var element = document.getElementById("cb103");
        customTriggerEvent(element, "touchstart");
        customTriggerEvent(element, "touchend");
    }

    navigateToSUE = function () {
        var element = document.getElementById("cb104");
        customTriggerEvent(element, "touchstart");
        customTriggerEvent(element, "touchend");
    }

    notifierPopup = function () {       
        setTimeout(function () {
            showConfirmationPopup();
        },
        0);
        setTimeout(function () {
            closeConfirmationPopup();
        },
        2000);
    }

    notifyAndNavigate = function () {       
        setTimeout(function () {
            showConfirmationPopup();
        },
        0);
        setTimeout(function () {
            closeConfirmationPopup();
        },
        2000);
        setTimeout(function () {
            navigate();
        },
        2000);
    }

    var customTriggerEvent = function (eventTarget, eventType, triggerExtra) {
        var evt = document.createEvent("HTMLEvents");
        evt.initEvent(eventType, true, true);
        evt.view = window;
        evt.altKey = false;
        evt.ctrlKey = false;
        evt.shiftKey = false;
        evt.metaKey = false;
        evt.keyCode = 0;
        evt.charCode = 'a';
        if (triggerExtra != null)
            evt.triggerExtra = triggerExtra;
        eventTarget.dispatchEvent(evt);
    };

    invokePopup = function () {
        var args = arguments;
        if (null != args[0] || args[0] != '') {
            var element = document.getElementById(args[0]);
            customTriggerEvent(element, "touchstart");
            customTriggerEvent(element, "touchend");
        }
    }
})();