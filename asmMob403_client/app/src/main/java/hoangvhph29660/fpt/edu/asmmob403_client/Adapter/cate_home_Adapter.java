package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;

public class cate_home_Adapter extends RecyclerView.Adapter<cate_home_Adapter.cateHolder> {
    Context context;
    ArrayList<cateModel> listCate;

    public cate_home_Adapter(Context context, ArrayList<cateModel> listCate) {
        this.context = context;
        this.listCate = listCate;
    }

    @NonNull
    @Override
    public cateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cate_home , parent , false);
        return new cateHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull cateHolder holder, int position) {
        cateModel cate = listCate.get(position);
        holder.txt_cate_name.setText(cate.getNameCate());


        holder.txt_cate_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (listCate != null){
            return listCate.size();
        }
        return 0;
    }

    public class cateHolder extends RecyclerView.ViewHolder{
        TextView txt_cate_name;

        public cateHolder(@NonNull View itemView) {
            super(itemView);
            txt_cate_name = itemView.findViewById(R.id.txt_cate_name);
        }

    }
}
