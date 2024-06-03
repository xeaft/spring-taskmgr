let pfpTexts = document.getElementsByClassName("user-pfp");

for (let pfpText of pfpTexts) {
    if (pfpText.tagName.toLowerCase() === "p") {
        pfpText.innerText = pfpText.innerText[0].toUpperCase()
    }
}