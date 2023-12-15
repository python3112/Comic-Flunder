package hoangvhph29660.fpt.edu.asmmob403_client.model;

import java.util.ArrayList;

public class cateModel {
    String _id ;
    String nameCate;

    ArrayList<String> id_comics;

    public cateModel(String _id, String nameCate, ArrayList<String> id_comics) {
        this._id = _id;
        this.nameCate = nameCate;
        this.id_comics = id_comics;
    }

    public cateModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }

    public ArrayList<String> getId_comics() {
        return id_comics;
    }

    public void setId_comics(ArrayList<String> id_comics) {
        this.id_comics = id_comics;
    }
}
