package com.zzh.commentwithreply.bean;

import java.util.List;

public class ActionBean {


    /**
     * code : 1
     * data : [{"id":"41","nickName":"独自悲伤","userLogo":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg","content":"时间是一切财富中最宝贵的财富。","createDate":"一分钟前"},{"id":"42","nickName":"独自悲伤","userLogo":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg","content":"时间是一切财富中最宝贵的财富。","createDate":"三分钟前","replyList":[{"nickName":"吴庚","status":"1","content":" 时间总是在不经意中擦肩而过,不留一点痕迹."}]},{"id":"43","nickName":"独自悲伤","userLogo":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg","content":"今日的事情，尽心、尽意、尽力去做了，无论成绩如何，都应该高高兴兴地上床恬睡。","createDate":"8分钟前","replyList":[{"nickName":"吴庚","status":"1","content":"我失眠"},{"nickName":"吴庚","status":"2","authorNickName":"独自悲伤","authorReplyContent":"来一榔头就睡着了"}]},{"id":"44","nickName":"独自悲伤","userLogo":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg","content":"想干就干，要干就干的漂亮，即使没有人为你鼓掌，至少还能够勇敢的自我欣赏。当你为自己的坎坷人生不断鼓掌，加油、奋进时，必将迎来别人的掌声与喝彩","createDate":"15分钟前","replyList":[{"nickName":"吴庚","status":"1","content":"我失眠"},{"nickName":"吴庚","status":"3","userNickName":"独自悲伤","userReplyContent":"来一榔头就睡着了"}]},{"id":"44","nickName":"独自悲伤","userLogo":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg","content":"如果放弃太早，你永远都不知道自己会错过什么。","createDate":"40分钟前","replyList":[{"nickName":"吴庚","status":"1","content":"说的太对了"},{"nickName":"吴庚","status":"2","authorNickName":"独自悲伤","authorReplyContent":"就你喜欢评论我"},{"nickName":"吴庚","status":"4","authorNickName":"独自悲伤","authorReplyContent":"哈哈，因为我最好了"}]}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 41
         * nickName : 独自悲伤
         * userLogo : https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg
         * content : 时间是一切财富中最宝贵的财富。
         * createDate : 一分钟前
         * replyList : [{"nickName":"吴庚","status":"1","content":" 时间总是在不经意中擦肩而过,不留一点痕迹."}]
         */

        private String id;
        private String nickName;
        private String userLogo;
        private String content;
        private String createDate;
        private List<String> images;
        private List<ReplyListBean> replyList;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserLogo() {
            return userLogo;
        }

        public void setUserLogo(String userLogo) {
            this.userLogo = userLogo;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            /**
             * nickName : 吴庚
             * status : 1
             * content :  时间总是在不经意中擦肩而过,不留一点痕迹.
             */

            private String nickName;
            private String status;
            private String content;

            private String authorNickName;
            private String authorReplyContent;
            private String userNickName;
            private String userReplyContent;

            public String getAuthorNickName() {
                return authorNickName;
            }

            public void setAuthorNickName(String authorNickName) {
                this.authorNickName = authorNickName;
            }

            public String getAuthorReplyContent() {
                return authorReplyContent;
            }

            public void setAuthorReplyContent(String authorReplyContent) {
                this.authorReplyContent = authorReplyContent;
            }

            public String getUserNickName() {
                return userNickName;
            }

            public void setUserNickName(String userNickName) {
                this.userNickName = userNickName;
            }

            public String getUserReplyContent() {
                return userReplyContent;
            }

            public void setUserReplyContent(String userReplyContent) {
                this.userReplyContent = userReplyContent;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            @Override
            public String toString() {
                return "ReplyListBean{" +
                        "nickName='" + nickName + '\'' +
                        ", status='" + status + '\'' +
                        ", content='" + content + '\'' +
                        ", authorNickName='" + authorNickName + '\'' +
                        ", authorReplyContent='" + authorReplyContent + '\'' +
                        ", userNickName='" + userNickName + '\'' +
                        ", userReplyContent='" + userReplyContent + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", userLogo='" + userLogo + '\'' +
                    ", content='" + content + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", images=" + images +
                    ", replyList=" + replyList +
                    '}';
        }
    }


}
