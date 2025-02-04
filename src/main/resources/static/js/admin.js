/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

document.querySelector("#image_file_input").addEventListener('change', function(event) {
    var file = event.target.files[0];
    var reader = new FileReader();

    reader.onload = function() {
        document.getElementById("upload_img_preview").src = reader.result;
    }
    reader.readAsDataURL(file);
});