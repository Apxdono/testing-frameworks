window['triggerEvent'] = function(e,t){var n;if(document.createEvent){n=document.createEvent("HTMLEvents");n.initEvent(t,true,true)}else{n=document.createEventObject();n.eventType=t}n.eventName=t;if(document.createEvent){e.dispatchEvent(n)}else{e.fireEvent("on"+n.eventType,n)}}