package com.example.administrator.myapplication.main;

import java.util.List;

/**
 * Created by lyn on 2016/9/19.
 */
public class GsonFormatTest {
    /**
     * success : true
     * message :
     * encoding : UTF-8
     * model : {"pageNum":1,"pageSize":20,"size":9,"orderBy":null,"startRow":1,"endRow":9,"total":9,"pages":1,"list":[{"id":110,"type":"contacts","creatTime":1472276925862,"uid":137,"account":"13456760313","message":"{\"account\":\"13456760313\",\"email\":\"13456760313@139.com\",\"id\":65,\"isLeader\":\"0\",\"job\":\"员工\",\"mobile\":\"13456760313\",\"name\":\"刘鼎\",\"sex\":\"0\",\"status\":1}"},{"id":109,"type":"jpeg","creatTime":1472276919954,"uid":137,"account":"","message":"/upload/collect/e2965a0313340ce7f086dd4532dbaddf.jpeg"},{"id":108,"type":"mp4","creatTime":1472276917917,"uid":137,"account":"","message":"/upload/collect/c90ecd58a62f8561bc964cfed1a66c65.mp4"},{"id":107,"type":"mp4","creatTime":1472276913544,"uid":137,"account":"","message":"/upload/collect/31fd79ea2cd0040680f0f88d4f5d245a.mp4"},{"id":106,"type":"txt","creatTime":1472276891573,"uid":137,"account":"","message":"大傻逼"},{"id":105,"type":"jpeg","creatTime":1472276857775,"uid":137,"account":"","message":"/upload/collect/3a49f0bb4e1a3f27bbbaec5bf01e76cb.jpeg"},{"id":98,"type":"txt","creatTime":1472094057289,"uid":137,"account":"","message":"哦哦"},{"id":97,"type":"mp4","creatTime":1472094039108,"uid":137,"account":"","message":"/upload/collect/8bf47a69b6832eb86361affd8f66a673.mp4"},{"id":96,"type":"amr","creatTime":1472090992667,"uid":137,"account":"","message":"/upload/collect/a23a0be693cdeb7dbd16cb762d6c0b7d.amr"}],"firstPage":1,"prePage":0,"nextPage":0,"lastPage":1,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1]}
     * pageVo : null
     */

    private boolean success;
    private String message;
    private String encoding;
    /**
     * pageNum : 1
     * pageSize : 20
     * size : 9
     * orderBy : null
     * startRow : 1
     * endRow : 9
     * total : 9
     * pages : 1
     * list : [{"id":110,"type":"contacts","creatTime":1472276925862,"uid":137,"account":"13456760313","message":"{\"account\":\"13456760313\",\"email\":\"13456760313@139.com\",\"id\":65,\"isLeader\":\"0\",\"job\":\"员工\",\"mobile\":\"13456760313\",\"name\":\"刘鼎\",\"sex\":\"0\",\"status\":1}"},{"id":109,"type":"jpeg","creatTime":1472276919954,"uid":137,"account":"","message":"/upload/collect/e2965a0313340ce7f086dd4532dbaddf.jpeg"},{"id":108,"type":"mp4","creatTime":1472276917917,"uid":137,"account":"","message":"/upload/collect/c90ecd58a62f8561bc964cfed1a66c65.mp4"},{"id":107,"type":"mp4","creatTime":1472276913544,"uid":137,"account":"","message":"/upload/collect/31fd79ea2cd0040680f0f88d4f5d245a.mp4"},{"id":106,"type":"txt","creatTime":1472276891573,"uid":137,"account":"","message":"大傻逼"},{"id":105,"type":"jpeg","creatTime":1472276857775,"uid":137,"account":"","message":"/upload/collect/3a49f0bb4e1a3f27bbbaec5bf01e76cb.jpeg"},{"id":98,"type":"txt","creatTime":1472094057289,"uid":137,"account":"","message":"哦哦"},{"id":97,"type":"mp4","creatTime":1472094039108,"uid":137,"account":"","message":"/upload/collect/8bf47a69b6832eb86361affd8f66a673.mp4"},{"id":96,"type":"amr","creatTime":1472090992667,"uid":137,"account":"","message":"/upload/collect/a23a0be693cdeb7dbd16cb762d6c0b7d.amr"}]
     * firstPage : 1
     * prePage : 0
     * nextPage : 0
     * lastPage : 1
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     */

    private ModelBean model;
    private Object pageVo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public Object getPageVo() {
        return pageVo;
    }

    public void setPageVo(Object pageVo) {
        this.pageVo = pageVo;
    }

    public static class ModelBean {
        private int pageNum;
        private int pageSize;
        private int size;
        private Object orderBy;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;
        private int firstPage;
        private int prePage;
        private int nextPage;
        private int lastPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        /**
         * id : 110
         * type : l
         * creatTime : 1472276925862
         * uid : 137
         * account : 13456760313
         * message : {"account":"13456760313","email":"13456760313@139.com","id":65,"isLeader":"0","job":"员工","mobile":"13456760313","name":"刘鼎","sex":"0","status":1}
         */

        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            private int id;
            private String type;
            private long creatTime;
            private int uid;
            private String account;
            private String message;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public long getCreatTime() {
                return creatTime;
            }

            public void setCreatTime(long creatTime) {
                this.creatTime = creatTime;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
