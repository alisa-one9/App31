package com.example.app31.ui.onBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app31.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private NavController navController;
    private View.OnClickListener onClickListener;

    private int [] images = new int []{R.drawable.claudiashiffer,R.drawable.bradpit,R.drawable.jolie_wallpaper};
    private String [] titles =new String[]{"Hello, Do you want to be successful?","This is not easy abd difficult path!","Fear nothing, and go ahead!"};
    private String[]titleDesc = new String[]{"Folllow us","We are strong power together","You will know more"};
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle, textDesc;
        private ImageView imageView;
        private Button btnStart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageBoard);
            textTitle = itemView.findViewById(R.id.textBoard);
            textDesc =itemView.findViewById(R.id.textDesc);
            btnStart = itemView.findViewById(R.id.btnStart);
        }

        public void bind(int position) {

            if(position== titles.length-1) {
                btnStart.setVisibility(View.VISIBLE);
            }else{
                btnStart.setVisibility(View.GONE);
            };

            textTitle.setText(titles[position]);
            textDesc.setText(titleDesc[position]);
            imageView.setImageResource(images[position]);


        }
    }
}
