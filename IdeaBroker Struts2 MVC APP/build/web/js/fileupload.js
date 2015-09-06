/**
 * Created with IntelliJ IDEA.
 * User: hevora
 * Date: 04-06-2013
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
var W3CDOM = (document.createElement && document.getElementsByTagName);

function initFileUploads() {
    if (!W3CDOM) return;
    var fakeFileUpload = document.createElement('div');
    fakeFileUpload.className = 'fakefile';
    fakeFileUpload.appendChild(document.createElement('input'));
    var image = document.createElement('img');
    image.src='pix/button_select.gif';
    fakeFileUpload.appendChild(image);
    var x = document.getElementsByTagName('input');
    for (var i=0;i<x.length;i++) {
        if (x[i].type != 'file') continue;
        if (x[i].parentNode.className != 'fileinputs') continue;
        x[i].className = 'file hidden';
        var clone = fakeFileUpload.cloneNode(true);
        x[i].parentNode.appendChild(clone);
        x[i].relatedElement = clone.getElementsByTagName('input')[0];
        x[i].onchange = x[i].onmouseout = function () {
            this.relatedElement.value = this.value;
        }
    }
}