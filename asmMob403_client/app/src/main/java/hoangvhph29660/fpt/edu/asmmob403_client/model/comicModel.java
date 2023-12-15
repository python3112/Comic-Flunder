package hoangvhph29660.fpt.edu.asmmob403_client.model;

import java.util.ArrayList;

public class comicModel {
    String _id , nameComic , description  ,author , dateRelease , coverImage , id_category;
    ArrayList<String> contentImage;

    ArrayList<String> id_coment;


    public comicModel() {
    }

    public comicModel(String _id, String nameComic, String description, String author, String dateRelease, String coverImage, String id_category, ArrayList<String> contentImage, ArrayList<String> id_coment) {
        this._id = _id;
        this.nameComic = nameComic;
        this.description = description;
        this.author = author;
        this.dateRelease = dateRelease;
        this.coverImage = coverImage;
        this.id_category = id_category;
        this.contentImage = contentImage;
        this.id_coment = id_coment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameComic() {
        return nameComic;
    }

    public void setNameComic(String nameComic) {
        this.nameComic = nameComic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public ArrayList<String> getContentImage() {
        return contentImage;
    }

    public void setContentImage(ArrayList<String> contentImage) {
        this.contentImage = contentImage;
    }

    public ArrayList<String> getId_coment() {
        return id_coment;
    }

    public void setId_coment(ArrayList<String> id_coment) {
        this.id_coment = id_coment;
    }
}
