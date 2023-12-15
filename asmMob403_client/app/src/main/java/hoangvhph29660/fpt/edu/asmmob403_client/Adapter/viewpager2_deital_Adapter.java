package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hoangvhph29660.fpt.edu.asmmob403_client.fragments.cmt_comic_fragment;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.deital_comic_fragment;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.read_comic_fragment;

public class viewpager2_deital_Adapter extends FragmentStateAdapter {
    public viewpager2_deital_Adapter( FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new cmt_comic_fragment();
            case 1:
                return new deital_comic_fragment();
            case 2:
                return new read_comic_fragment();

            default:
                return  new deital_comic_fragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
