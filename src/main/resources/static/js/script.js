/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

let currenttheme = getTheme();


document.addEventListener('DOMContentLoaded', () => {
    changeTheme();
})


// chane the theme
function changeTheme() {
    document.querySelector('html').classList.add(currenttheme)
    setLocalStorageAndChangeTheme(currenttheme, currenttheme);
    // set the listener to cnage the theme
    document.querySelector('#theme_change_btn').addEventListener('click', () => {
        const oldTheme = currenttheme;
        if (currenttheme === 'dark')
            currenttheme = "light";
        else
            currenttheme = "dark";
        setLocalStorageAndChangeTheme(oldTheme, currenttheme);
    })
}

// set theme to localstorage
function setTheme(theme) {
    localStorage.setItem("theme", theme)
}

// get theme from locatstorage
function getTheme() {
    let theme = localStorage.getItem("theme");
    if (theme)
        return theme;
    else
    return document.querySelector('#theme_change_btn').querySelector("span").textContent.toLowerCase();
}

// change the theme
function setLocalStorageAndChangeTheme(oldTheme, currenttheme) {
    // call set theme
    setTheme(currenttheme);
    // removed the current theme from the html class
    document.querySelector('html').classList.remove(oldTheme);
    // add the current theme value to the html class
    document.querySelector('html').classList.add(currenttheme);
    //change the text of the button
    document.querySelector('#theme_change_btn').querySelector("span").textContent = currenttheme === "light" ? "Dark" : "Light"; 
}