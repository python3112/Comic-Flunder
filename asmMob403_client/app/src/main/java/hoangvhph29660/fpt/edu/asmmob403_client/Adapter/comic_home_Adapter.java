package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.Activitis.Deital_comic;
import hoangvhph29660.fpt.edu.asmmob403_client.Activitis.Deital_comic_user;
import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.comicModel;

public class comic_home_Adapter extends RecyclerView.Adapter<comic_home_Adapter.comicHoler>{


    Context context;
    ArrayList<comicModel> listComic;

    public comic_home_Adapter(Context context, ArrayList<comicModel> listComic) {
        this.context = context;
        this.listComic = listComic;
    }

    @NonNull
    @Override
    public comicHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic_home , parent , false);

        return new comicHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull comicHoler holder, int position) {
        comicModel comicHoler = listComic.get(position);
        Glide.with(context).load(MainActivity.urlCover +comicHoler.getCoverImage()).into(holder.img_comic_item);
        holder.txt_namecomic_item.setText(comicHoler.getNameComic());
        holder.auther_comic_item.setText(comicHoler.getAuthor());

        holder.img_comic_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , Deital_comic_user.class);
                intent.putExtra("id_comic" , comicHoler.get_id());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listComic != null){
            return listComic.size();
        }
        return 0;
    }

    public class comicHoler extends RecyclerView.ViewHolder{
        ImageView img_comic_item;
        TextView txt_namecomic_item,  auther_comic_item;
        public comicHoler(@NonNull View itemView) {
            super(itemView);
            img_comic_item = itemView.findViewById(R.id.img_comic_item);
            txt_namecomic_item = itemView.findViewById(R.id.txt_namecomic_item);
            auther_comic_item = itemView.findViewById(R.id.auther_comic_item);

        }
    }
}
