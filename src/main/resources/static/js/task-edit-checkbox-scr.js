function handleSubmit(event) {
    const form = event.target;
    const checkbox = form.querySelector('input[name="completed"][type="checkbox"]');
    const hiddenInput = form.querySelector('input[name="completed"][type="hidden"]');

    if (checkbox.checked) {
        hiddenInput.disabled = true;
    } else {
        hiddenInput.disabled = false;
    }

    return false
}