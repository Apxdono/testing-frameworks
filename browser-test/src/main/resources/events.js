Event=(function(){var a=0;function c(f){f=f||window.event;if(f.isFixed){return f}f.isFixed=true;f.preventDefault=f.preventDefault||function(){this.returnValue=false};f.stopPropagation=f.stopPropagaton||function(){this.cancelBubble=true};if(!f.target){f.target=f.srcElement}if(!f.relatedTarget&&f.fromElement){f.relatedTarget=f.fromElement==f.target?f.toElement:f.fromElement}if(f.pageX==null&&f.clientX!=null){var e=document.documentElement,d=document.body;f.pageX=f.clientX+(e&&e.scrollLeft||d&&d.scrollLeft||0)-(e.clientLeft||0);f.pageY=f.clientY+(e&&e.scrollTop||d&&d.scrollTop||0)-(e.clientTop||0)}if(!f.which&&f.button){f.which=(f.button&1?1:(f.button&2?3:(f.button&4?2:0)))}return f}function b(i){i=c(i);var d=this.events[i.type];for(var h in d){var f=d[h];var e=f.call(this,i);if(e===false){i.preventDefault();i.stopPropagation()}}}return{add:function(f,e,d){if(f.setInterval&&(f!=window&&!f.frameElement)){f=window}if(!d.guid){d.guid=++a}if(!f.events){f.events={};f.handle=function(g){if(typeof Event!=="undefined"){return b.call(f,g)}}}if(!f.events[e]){f.events[e]={};if(f.addEventListener){f.addEventListener(e,f.handle,false)}else{if(f.attachEvent){f.attachEvent("on"+e,f.handle)}}}f.events[e][d.guid]=d},remove:function(g,f,e){var d=g.events&&g.events[f];if(!d){return}delete d[e.guid];for(var h in d){return}if(g.removeEventListener){g.removeEventListener(f,g.handle,false)}else{if(g.detachEvent){g.detachEvent("on"+f,g.handle)}}delete g.events[f];for(var h in g.events){return}delete g.handle;delete g.events}}}());
