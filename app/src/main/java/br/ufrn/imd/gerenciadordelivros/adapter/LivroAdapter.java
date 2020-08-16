package br.ufrn.imd.gerenciadordelivros.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufrn.imd.gerenciadordelivros.R;
import br.ufrn.imd.gerenciadordelivros.dominio.Livro;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroMolder> {

    private List<Livro> livros;
    private Context context;

    private  OnLivroListener onLivroListener;

    public LivroAdapter(List<Livro> livros, Context context, OnLivroListener onLivroListener) {
        this.livros = livros;
        this.context = context;
        this.onLivroListener = onLivroListener;
    }

    @NonNull
    @Override
    public LivroMolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_livro, parent, false);

        LivroMolder livroMolder = new LivroMolder(view, onLivroListener);

        return livroMolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LivroMolder holder, int position) {
        Livro livro = livros.get(position);

        holder.txtTitulo.setText(livro.getTitulo());
        holder.txtAutor.setText(livro.getAutor());
        holder.txtEditora.setText(livro.getEditora());

        if(livro.getEmprestado() == 1){
            holder.ic_livro.setColorFilter(Color.GRAY);
            holder.ic_star.setVisibility(View.VISIBLE);
        } else{
            holder.ic_livro.setColorFilter(Color.parseColor("#0455BF"));
            holder.ic_star.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    public void setItems(List<Livro> livros){
        this.livros = livros;
    }

    public Livro getItem(int posicao){
        return  livros.get(posicao);
    }

    public class LivroMolder extends  RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtTitulo;
        public TextView txtAutor;
        public TextView txtEditora;
        public ImageView ic_livro;
        public ImageView ic_star;

        public OnLivroListener onLivroListener;

        public LivroMolder(View view, OnLivroListener onLivroListener){
            super(view);

            txtTitulo = view.findViewById(R.id.txtTitulo);
            txtAutor = view.findViewById(R.id.txtAutor);
            txtEditora = view.findViewById(R.id.txtEditora);
            ic_livro = view.findViewById(R.id.ic_livro);
            ic_star = view.findViewById(R.id.ic_star);

            this.onLivroListener = onLivroListener;

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onLivroListener.onLivroClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onLivroListener.onLivroLongClick(getAdapterPosition());

            return true;
        }
    }

    public interface OnLivroListener{
        void onLivroClick (int posicao);
        void onLivroLongClick (int posicao);
    }

}
