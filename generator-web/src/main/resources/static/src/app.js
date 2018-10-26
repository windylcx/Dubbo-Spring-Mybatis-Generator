/**
 * Created by chunxiaoli on 5/10/17.
 */
const $ = require('jquery');
window.$ = window.jQuery = $;
require('bootstrap');
import * as Mustache from "mustache";
import Upload from "./upload.js";
import User from "./user.js";

export default class App {

    constructor() {
        this.waiting=false;
        if (!window.java) {
            window.java = {
                log: function (msg) {
                    console.log(msg);
                },
                loadDefaultConfig: function () {
                    console.log("loadDefaultConfig");
                }
            };
        }
    }


    init() {
        console.log("init...");

        $('.generate').click((e) => {
            if(this.waiting){
                return;
            }
            this.waiting=true;

            try{
                $(e.target).html("请稍候.....");

                let config = {};

                $("."+$(e.target).attr("data-type")).find(".config-item").each(function (i, item) {
                    //console.log(i, item);
                    config[item.name] = item.value;
                });


                //console.log(config['tables_sql']);

                if(config['tables_sql'].trim()!==""){
                    let list = config['tables_sql'].match(/(CREATE TABLE|create table) (.*) \(/g);
                    let tables = [];
                    list.forEach((item) => {
                        const table = item.replace(/CREATE TABLE/g, "").replace(/\(/g, "").replace(/`/g, "").trim();
                        console.log(table);
                        tables.push(table);
                    });
                    config["tables"] = tables.join(",");

                    let sql = $("#tables_sql").val().trim().replace(/^#.*$/gm, "").replace(/\/\*.*\*\/;/g, "").replace(/\n/g, "");

                    config['tables_sql'] = Base64.encode(sql);
                }

                const params = JSON.stringify(config);


                console.log("config", config['tables_sql'], config, params);


                $.ajax({
                    type: "POST",
                    url: "generate?client_id=" + User.getClientId(),
                    contentType: "application/json",

                    data: params,

                    success: (data) => {
                        console.log(data);

                        const url = "download?client_id=" + User.getClientId()
                            + "&id=" + data+"&name="+(config["name"]?config["name"]:"test");

                        let iframe = document.createElement('iframe');

                        $(iframe).css({
                            display: 'none',
                            width: 0,
                            height: 0
                        });

                        iframe.onload = function () {
                            $(iframe).remove();
                            iframe = null;
                            console.log('remove');
                        };

                        iframe.src = url;
                        document.body.appendChild(iframe);

                        $(e.target).html("生成");
                        this.waiting=false;
                    },
                    error: (err) => {
                        alert(err);
                        console.log(err);
                        $(e.target).html("生成");
                        this.waiting=false;
                    }
                });

            }catch(err){
                alert(err);
                $(e.target).html("生成");
                this.waiting=false;
            }
            
            

        });

        $(".file-upload").on('change', (e) => {
            let file = $(e.target)[0].files[0];
            console.log("change", file, $(e.target), $(e.target).attr("name"));
            let upload = new Upload($(e.target).attr("name"), file);
            upload.doUpload();
        });


        $("#load").click((e) => {
            console.log("load", e);

        });

        console.log("init...");

        this.loadDefaultConfig();

    }

    loadDefaultConfig() {
        $.ajax("load_config?client_id="+User.getClientId()).then((config)=>{
            //let config = JSON.parse(d);
            let data = {items: []};
            for (let p in config) {
                java.log(p + ":" + config[p]);
                data.items.push({
                    key: p,
                    value: config[p]
                });
            }
            $.get('tpl/template.mst', (template) => {
                let rendered = Mustache.render(template, data);
                $("#defaultConfig").html(rendered);
            }).fail((err) => {
                java.log(err);
            });
        }).catch(err=>{
            console.log("err",err);
        })


    }


}
$(document).ready(() => {
    //$('[data-toggle="tooltip"]').tooltip();
    console.log("dom ready");
    new App().init();
});



