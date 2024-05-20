let docs = document.getElementsByClassName('settingsForm');

for (let doc of docs) {
    doc.addEventListener('submit', function(event) {
        event.preventDefault();

        const checkbox = document.getElementById('firstSetting');
        const csrfCookie = document.querySelector('[name="_csrf"]').value;

        const payload = {
            _csrf: csrfCookie,
            name: checkbox.name,
            value: checkbox.checked.toString(),
        };

        fetch('/settings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfCookie
            },
            body: JSON.stringify(payload)
        }).then(response => {
            if (!response.ok) {
                console.log('Error: ' + response.statusText);
                return;
           }
        }).catch(error => {
            console.log('Error: ' + error);
        });
    });
}
