package com.chunxiao.dev.pojo;

import java.util.List;

/**
 * Created by chunxiaoli on 6/14/17.
 */
public class PostmanCollection {

    private List<Variables>  variables;
    private Info             info;
    private List<FolderItem> folderItems;

    public List<Variables> getVariables() {
        return variables;
    }

    public void setVariables(List<Variables> variables) {
        this.variables = variables;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<FolderItem> getFolderItems() {
        return folderItems;
    }

    public void setFolderItems(List<FolderItem> folderItems) {
        this.folderItems = folderItems;
    }

    class Variables {

    }

    class Info {
        private String name;
        private String id;
        private String description;
        private String schema;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }
    }

    class FolderItem {
        private String        name;
        private String        description;
        private List<CgiItem> cgiItems;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<CgiItem> getCgiItems() {
            return cgiItems;
        }

        public void setCgiItems(List<CgiItem> cgiItems) {
            this.cgiItems = cgiItems;
        }
    }

    class CgiItem {

        private String   name;
        private Event    event;
        private Request  request;
        private Response response;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }

        public Request getRequest() {
            return request;
        }

        public void setRequest(Request request) {
            this.request = request;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        class Event {
            private String listen;
            private Script script;

            public String getListen() {
                return listen;
            }

            public void setListen(String listen) {
                this.listen = listen;
            }

            public Script getScript() {
                return script;
            }

            public void setScript(Script script) {
                this.script = script;
            }

            class Script {
                private String       type;
                private List<String> exec;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<String> getExec() {
                    return exec;
                }

                public void setExec(List<String> exec) {
                    this.exec = exec;
                }
            }
        }

        class Request {
            private String       url;
            private String       method;
            private String       description;
            private List<Header> header;
            private Body         body;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<Header> getHeader() {
                return header;
            }

            public void setHeader(List<Header> header) {
                this.header = header;
            }

            public Body getBody() {
                return body;
            }

            public void setBody(Body body) {
                this.body = body;
            }

            class Header {

                class HeaderItem {
                    private String key;
                    private String value;
                    private String description;

                    public String getKey() {
                        return key;
                    }

                    public void setKey(String key) {
                        this.key = key;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }
                }
            }

            class Body {
                private String mode;
                private String raw;

                public String getMode() {
                    return mode;
                }

                public void setMode(String mode) {
                    this.mode = mode;
                }

                public String getRaw() {
                    return raw;
                }

                public void setRaw(String raw) {
                    this.raw = raw;
                }
            }
        }

        class Response {

        }
    }

    /*
    "name": "收货地址创建",
					"event": [
						{
							"listen": "test",
							"script": {
								"type": "text/javascript",
								"exec": [
									"var data = JSON.parse(responseBody);",
									"tests[\"创建收货地址\"] = data.code === 0;"
								]
							}
						}
					],
					"request": {
						"url": "http://{{host}}:{{port}}/chunxiaomall_app/v1/consignee_address/create",
						"method": "POST",
						"header": [
							{
								"key": "Content-Type",
								"value": "application/json",
								"description": ""
							},
							{
								"key": "session_key",
								"value": "{{session_key}}",
								"description": ""
							},
							{
								"key": "app_id",
								"value": "{{app_id}}",
								"description": ""
							}
						],
						"body": {
							"mode": "raw",
							"raw": "{\n\t\"province\":\"广东省\",\n\t\"city\":\"深圳市\",\n\t\"consignee_name\":\"李春晓\",\n\t\"district\":\"福田区\",\n\t\"mobile\":\"18818997517\",\n\t\"detail\":\"望海路1106号招商局广场30楼\",\n\t\"is_default\":2\n}"
						},
						"description": ""
					},
					"response": []
     */

}
