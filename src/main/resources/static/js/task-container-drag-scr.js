let navbar = document.getElementById('leftnavbar');
document.querySelectorAll('.drag-header').forEach(header => {
    header.addEventListener('mousedown', function (e) {
        const mainContainer = this.parentElement;
        let offsetX = e.clientX - mainContainer.getBoundingClientRect().left + navbar.offsetWidth;
        let offsetY = e.clientY - mainContainer.getBoundingClientRect().top;
        let isDragging = true;

        header.style.cursor = 'grabbing';

        const onMouseMove = function (e) {
            if (isDragging) {
                mainContainer.style.left = `${Math.min(Math.max(e.clientX - offsetX, 0), window.innerWidth - mainContainer.offsetWidth)}px`;
                mainContainer.style.top = `${e.clientY - offsetY}px`;
            }
        };

        const onMouseUp = function () {
            isDragging = false;
            header.style.cursor = 'grab';
            document.removeEventListener('mousemove', onMouseMove);
            document.removeEventListener('mouseup', onMouseUp);
        };

        document.addEventListener('mousemove', onMouseMove);
        document.addEventListener('mouseup', onMouseUp);
    });
});