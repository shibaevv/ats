YAHOO.namespace('ats.setup');

(function() {
    var loader = new YAHOO.util.YUILoader({
        onSuccess: function() {
    	    YAHOO.ats.cleanup();

    	    YAHOO.ats.setup.initMenu = function(title, menuItems) {
    	        var innerHTML = '<div id="menuBar" class="yuimenubar yuimenubarnav"><div class="bd"><ul class="first-of-type">';
    	        for (var i = 0; i != menuItems.length; i++) {
    	            var menuItem = menuItems[i];
    	            innerHTML = innerHTML +
    	                '<li class="yuimenubaritem' + (i == 0 ? ' first-of-type' : '') + '">' +
    	                    '<a class="yuimenubaritemlabel" href="javascript:;">' + menuItem.text + '</a>' +
    	                '</li>';
    	        }
    	        innerHTML = innerHTML + '</ul></div></div>';
    	        var d = YAHOO.util.Dom.get('menuBarDiv');
    	        d.innerHTML = innerHTML;

    	        YAHOO.ats.setup.menuBar = new YAHOO.widget.MenuBar('menuBar', {
    	            autosubmenudisplay: false,
    	            keepopen: false
    	        });
    	        YAHOO.ats.setup.menuBar.subscribe('beforeRender', function () {
    	            if (this.getRoot() == this) {
    	                for (i = 0; i != menuItems.length; i++) {
    	                    this.getItem(i).cfg.setProperty('submenu', menuItems[i]);
    	                }
    	            }
    	        });
    	        YAHOO.ats.setup.menuBar.render();

    	        //Setup the click listeners on the menuBar
    	        YAHOO.util.Event.on('menuBar', 'click', function(e) {
    	            var tar = YAHOO.util.Event.getTarget(e);
    	            if (tar.tagName.toLowerCase() != 'a') {
    	                tar = null;
    	            }
    	            if (tar && tar.href == 'javascript:;') {
    	                tar = null;
    	            }
    	            //Make sure we are a link in the list's 
    	            if (tar && YAHOO.util.Selector.test(tar, '#menuBar ul li a')) {
    	                YAHOO.util.Event.preventDefault(e);
    	                var action = tar.href;
    	                YAHOO.util.Dom.removeClass(YAHOO.util.Selector.query('#menuBar li'), 'selected');
    	                YAHOO.util.Dom.addClass(tar.parentNode, 'selected');
    	                //get data
    	                YAHOO.ats.sendGetRequest(action, YAHOO.ats.viewCallback);
    	            }
    	        });

    	        // update title
    	        var titleEl = YAHOO.util.Dom.get('menuCenter.title');
    	        titleEl.innerHTML = title;

    	        // show center menu
    	        var menuEl = YAHOO.util.Dom.get('menuCenter');
    	        if (YAHOO.util.Dom.hasClass(menuEl, 'hidden')) {
    	            YAHOO.util.Dom.removeClass(menuEl, 'hidden');
    	        }
    	    };

    	    YAHOO.ats.setup.findCallback = {
                cache:false,
                success: function(oResponse) {
                    //parse data
                    try {
                        var data = YAHOO.ats.parseJsonData(oResponse.responseText);
                        //load menu
                        YAHOO.ats.setup.initMenu(data.title, data.menuItems);
                    } catch (e) {
                        YAHOO.ats.alert('Invalid JSON data: ' + e, oResponse);
                    }
                },
                failure: function(oResponse) {
                    YAHOO.ats.failure(oResponse);
                }
                //, timeout: 10000
            };
            YAHOO.ats.sendGetRequest('setup/main.do?dispatch=find', YAHOO.ats.setup.findCallback);
        }
    });

    //Have loader insert only the JS files.
    //loader.insert({}, 'js');
    loader.insert();
})();