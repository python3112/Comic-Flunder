package hoangvhph29660.fpt.edu.asmmob403_client.model;

public class cmtModel {
    String _id , userId, comicId,content;
    String dateTime;

    public cmtModel() {
    }

    public cmtModel(String _id, String userId, String comicId, String content, String dateTime) {
        this._id = _id;
        this.userId = userId;
        this.comicId = comicId;
        this.content = content;
        this.dateTime = dateTime;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
