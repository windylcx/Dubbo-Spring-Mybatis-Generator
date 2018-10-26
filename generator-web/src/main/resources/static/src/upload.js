/**
 * Created by chunxiaoli on 5/10/17.
 */
const $ = require('jquery');
window.$ = window.jQuery = $;
import User from "./user.js";

class Upload {

    constructor(key, file) {
        this.file = file;
        this.key = key;
    }


    getType() {
        return this.file.type;
    }

    getSize() {
        return this.file.size;
    }

    getName() {
        return this.file.name;
    }

    doUpload(type) {
        var that = this;
        var formData = new FormData();

        // add assoc key values, this will be posts values
        formData.append("file",this.file,this.getName());
        formData.append("upload_file", true);

        $.ajax({
            type: "POST",
            url: "upload?client_id=" + User.getClientId() + "&key=" +this.key,
            xhr: function () {
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', that.progressHandling, false);
                }
                return myXhr;
            },
            success: function (data) {
                console.log("upload ok", data);
            },
            error: function (error) {
                // handle error
                console.log("upload fail", error);
            },
            async: true,
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            timeout: 60000
        });
    }

    progressHandling(event) {
        var percent = 0;
        var position = event.loaded || event.position;
        var total = event.total;
        var progress_bar_id = "#progress-wrp";
        if (event.lengthComputable) {
            percent = Math.ceil(position / total * 100);
        }
        // update progressbars classes so it fits your code
        //$(progress_bar_id + " .progress-bar").css("width", +percent + "%");
        //$(progress_bar_id + " .status").text(percent + "%");
        console.log(percent + "%");
    };
}
export default Upload;


