setMessage_MessageBox=function(g,f,e){var d=document.getElementById(g+"Content").getElementsByTagName("span")[0];var c=document.createElement("div");c.appendChild(document.createTextNode(f));c.className="MessageBox_TextTitle";c.id="messageBoxIDTitle";var b=document.createElement("div");b.innerHTML=e;
b.className="MessageBox_TextDescription";b.id="messageBoxIDMessage";var a=document.createElement("div");a.className="MessageBox_TextSeparator";d.appendChild(c);d.appendChild(b);d.appendChild(a);};initialize_MessageBox=function(b){setType_MessageBox(b,"HIDDEN");var a=document.getElementById(b+"Content").getElementsByTagName("span")[0];
while(a.hasChildNodes()){a.removeChild(a.lastChild);}};setValues_MessageBox=function(f,a,e,c){var b=document.getElementById(f).className.toUpperCase().replace("MESSAGEBOX","");var d=a.toUpperCase();if(b=="ERROR"){}else{if(b=="WARNING"){if(d=="ERROR"){b=d;}}else{if(b=="INFO"){if(d=="ERROR"||d=="WARNING"){b=d;
}}else{if(b=="SUCCESS"){if(d=="ERROR"||d=="WARNING"||d=="INFO"){b=d;}}else{if(b=="HIDDEN"){if(d=="ERROR"||d=="WARNING"||d=="INFO"||d=="SUCCESS"){b=d;}}}}}}setType_MessageBox(f,b);setMessage_MessageBox(f,e,c);};setType_MessageBox=function(b,a){document.getElementById(b).className="MessageBox"+a.toUpperCase();
};