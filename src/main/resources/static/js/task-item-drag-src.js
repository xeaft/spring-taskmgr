function getElementPosition(el) {
    const rect = el.getBoundingClientRect();
    const scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    return { top: rect.top + scrollTop, left: rect.left + scrollLeft, 
             bottom: rect.bottom + scrollTop, right: rect.right + scrollLeft };
}

doElsCollide = function(el1, el2) {
    const pos1 = getElementPosition(el1);
    const pos2 = getElementPosition(el2);
    
    return !(pos1.bottom < pos2.top ||
             pos1.top > pos2.bottom ||
             pos1.right < pos2.left ||
             pos1.left > pos2.right);
};



