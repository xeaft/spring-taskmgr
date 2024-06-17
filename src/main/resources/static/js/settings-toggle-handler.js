settings = document.getElementsByClassName("togglesetting");

for (let i = 0; i < settings.length; i++) {
    settings[i].addEventListener("change", function () {

        let checkbox = this.querySelector("input[type=checkbox]");


        this.querySelector("input[name=enabled]").value = checkbox.checked;


        let form = this.closest("form");
        let url = form.getAttribute("action");
        let data = new FormData(form);

        fetch(url, {
            method: "POST",
            body: data
        });
    });
}