package com.tassicompany.notas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tassicompany.notas.R;
import com.tassicompany.notas.dao.NotaDAO;
import com.tassicompany.notas.model.Nota;
import com.tassicompany.notas.ui.recyclerView.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;
    private List<Nota> notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);

        notas = notasDeExemplo();
        configuraRecyclerView(notas);

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivity(iniciaFormularioNota);
            }
        });
    }

    @Override
    protected void onResume() {
        NotaDAO notaDAO = new NotaDAO();
        notas.clear();
        notas.addAll(notaDAO.todos());
        //Verifica diferenças e realiza adaptação, criando novos View Holders apenas para itens novos.
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
        for (int i = 1; i <= 1000; i++) {
            dao.insere(new Nota("Titulo" + i, "conteúdo" + i));
        }
        List<Nota> notas = dao.todos();
        return notas;
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycler_view);
        configuraAdapter(notas, listaNotas);
//        configuraLayoutManager(listaNotas);
    }

    //Set Layout Manager programatically
//    private void configuraLayoutManager(RecyclerView listaNotas) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        listaNotas.setLayoutManager(linearLayoutManager);
//    }

    private void configuraAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
    }
}