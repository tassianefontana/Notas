package com.tassicompany.notas.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tassicompany.notas.R;
import com.tassicompany.notas.dao.NotaDAO;
import com.tassicompany.notas.model.Nota;
import com.tassicompany.notas.ui.recyclerView.ListaNotasAdapter;

import java.util.List;



public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycler_view);

        NotaDAO dao = new NotaDAO();
        for(int i = 1; i <= 1000; i++) {
            dao.insere(new Nota("Titulo" + i, "conteúdo" + i));
        }

        List<Nota> notas = dao.todos();
        listaNotas.setAdapter(new ListaNotasAdapter(this, notas));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(linearLayoutManager);

    }
}