package com.tassicompany.notas.ui.activity;

import static com.tassicompany.notas.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.tassicompany.notas.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tassicompany.notas.R;
import com.tassicompany.notas.dao.NotaDAO;
import com.tassicompany.notas.model.Nota;
import com.tassicompany.notas.ui.recyclerView.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    public static final String CHAVE_NOTA = "nota";
    private ListaNotasAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);
        List<Nota> notas = pegaTodasNotas();
        configuraRecyclerView(notas);

        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(view -> {
            vaiParaFormularioNotaActivity();
        });
    }

    private void vaiParaFormularioNotaActivity() {
        Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO notaDAO = new NotaDAO();
        List<Nota> notas = notaDAO.todos();
        return notas;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (eResultadoComNota(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            adicionaNota(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adicionaNota(Nota notaRecebida) {
        new NotaDAO().insere(notaRecebida);
        adapter.adicionaNota(notaRecebida);
    }

    private boolean eResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return eCodigoRequisicaoInsereNota(requestCode) && eCodigoResultadoNotaCriada(resultCode) && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean eCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean eCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

//    private List<Nota> notasDeExemplo() {
//        NotaDAO dao = new NotaDAO();
//        for (int i = 1; i <= 2; i++) {
//            dao.insere(new Nota("Titulo" + i, "conteúdo" + i));
//        }
//        return dao.todos();
//    }

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