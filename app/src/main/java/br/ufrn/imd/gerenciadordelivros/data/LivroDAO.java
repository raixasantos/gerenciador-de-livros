package br.ufrn.imd.gerenciadordelivros.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.gerenciadordelivros.dominio.Livro;

public class LivroDAO {
    private SQLiteDatabase bd;
    private static LivroDAO instance;

    private LivroDAO (Context context){
        DBHelper dbHelper = DBHelper.getInstance(context);
        bd = dbHelper.getWritableDatabase();
    }

    public static LivroDAO getInstance(Context context){
        if(instance == null){
            instance = new LivroDAO(context.getApplicationContext());
        }
        return instance;
    }

    private static Livro fromCursor(Cursor c){
        Long id = c.getLong(c.getColumnIndex(LivroContract.Columns._ID));
        String titulo = c.getString(c.getColumnIndex(LivroContract.Columns.titulo));
        String autor = c.getString(c.getColumnIndex(LivroContract.Columns.autor));
        String editora = c.getString(c.getColumnIndex(LivroContract.Columns.editora));
        int status = c.getInt(c.getColumnIndex(LivroContract.Columns.status));

        return new Livro(id, titulo, autor, editora, status);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Livro> list(){
        String[] columns = {
                LivroContract.Columns._ID,
                LivroContract.Columns.titulo,
                LivroContract.Columns.autor,
                LivroContract.Columns.editora,
                LivroContract.Columns.status
        };

        List<Livro> livros = new ArrayList<>();

        try( Cursor c = bd.query(LivroContract.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                LivroContract.Columns.titulo)){

            if(c.moveToFirst()){
                do {
                    Livro l = LivroDAO.fromCursor(c);
                    livros.add(l);

                }while (c.moveToNext());
            }

        }

        return livros;
    }

    public void save(Livro livro){
        ContentValues values = new ContentValues();

        values.put(LivroContract.Columns.titulo, livro.getTitulo());
        values.put(LivroContract.Columns.autor, livro.getAutor());
        values.put(LivroContract.Columns.editora, livro.getEditora());
        values.put(LivroContract.Columns.status, livro.getEmprestado());

        Long id = bd.insert(LivroContract.TABLE_NAME, null, values);
        livro.setId(id);
    }

    public void update(Livro livro){
        ContentValues values = new ContentValues();

        values.put(LivroContract.Columns.titulo, livro.getTitulo());
        values.put(LivroContract.Columns.autor, livro.getAutor());
        values.put(LivroContract.Columns.editora, livro.getEditora());
        values.put(LivroContract.Columns.status, livro.getEmprestado());

        bd.update(LivroContract.TABLE_NAME,
                values,
                LivroContract.Columns._ID + "=?",
                new String[]{String.valueOf(livro.getId())}
                );

    }

    public void delete(Livro livro){
        ContentValues values = new ContentValues();

        values.put(LivroContract.Columns.titulo, livro.getTitulo());
        values.put(LivroContract.Columns.autor, livro.getAutor());
        values.put(LivroContract.Columns.editora, livro.getEditora());
        values.put(LivroContract.Columns.status, livro.getEmprestado());

        bd.delete(LivroContract.TABLE_NAME,
                LivroContract.Columns._ID + "=?",
                new String[]{String.valueOf(livro.getId())}
                );
    }

}
