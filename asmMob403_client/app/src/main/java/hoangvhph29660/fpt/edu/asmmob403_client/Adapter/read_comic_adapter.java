package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;
import hoangvhph29660.fpt.edu.asmmob403_client.R;

public class read_comic_adapter extends RecyclerView.Adapter<read_comic_adapter.readcomicHoler>{
    Context context;

    ArrayList<String> listImgComic;


    public read_comic_adapter(Context context, ArrayList<String> listImgComic) {
        this.context = context;
        this.listImgComic = listImgComic;
    }

    @NonNull
    @Override
    public readcomicHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read_comic , parent , false);
        return new readcomicHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull readcomicHoler holder, int position) {
        String img = listImgComic.get(position);

        Glide.with(context).load(MainActivity.urlContent+img).into(holder.img_array_comic);

    }

    @Override
    public int getItemCount() {
        if(listImgComic!=null){
            return listImgComic.size();
        }
        return 0;
    }

    public class readcomicHoler extends RecyclerView.ViewHolder{
        ImageView img_array_comic;
        public readcomicHoler(@NonNull View itemView) {
            super(itemView);

            img_array_comic = itemView.findViewById(R.id.img_array_comic);
        }
    }
}
