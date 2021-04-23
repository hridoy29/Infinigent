package com.example.infinigentconsulting;

public class CommnetsClass {
    public int Id;
    public int CommentsTypeId;
    public String Comments;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public int getCommentsTypeId() {
        return CommentsTypeId;
    }

    public void setCommentsTypeId(int commentsTypeId) {
        CommentsTypeId = commentsTypeId;
    }
}
