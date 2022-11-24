package pt.ubi.di.pmd.play2learn_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DifficultyAdapter extends BaseAdapter {
    private Context context;
    private List<Dificulty> difList;

    public DifficultyAdapter(Context context, List<Dificulty> difList) {
        this.context = context;
        this.difList = difList;
    }

    @Override
    public int getCount() {
        return difList != null ? difList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_dif, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);

        if(difList.get(i).getNomeDif().equals("Easy")) {
            txtName.setText(view.getResources().getString(R.string.difEasy));
        }
        if(difList.get(i).getNomeDif().equals("Medium")) {
            txtName.setText(view.getResources().getString(R.string.difMedium));
        }
        if(difList.get(i).getNomeDif().equals("Hard")) {
            txtName.setText(view.getResources().getString(R.string.difHard));
        }

        return rootView;
    }
}
