let pfpText = document.getElementsByClassName("user-pfp")[0];

if (pfpText && pfpText.tagName.toLowerCase() === "p") {
    pfpText.innerText = pfpText.innerText[0].toUpperCase()
}

