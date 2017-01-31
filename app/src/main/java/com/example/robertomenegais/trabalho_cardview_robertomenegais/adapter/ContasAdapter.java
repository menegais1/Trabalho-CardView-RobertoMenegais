package com.example.robertomenegais.trabalho_cardview_robertomenegais.adapter;


        import android.content.Context;
        import android.net.Uri;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.example.robertomenegais.trabalho_cardview_robertomenegais.R;
        import com.example.robertomenegais.trabalho_cardview_robertomenegais.model.Conta;

        import java.util.List;

       


/**
 * Esta classe realiza a adaptação dos dados entre a RecyclerView <-> List.
 * Neste projeto a List está sendo alimentada com dados oriundos de um banco de dados SQLite.
 * @author Vagner Pinto da Silva
 */
public class ContasAdapter extends RecyclerView.Adapter<ContasAdapter.ContasViewHolder> {
    protected static final String TAG = "ContasAdapter";
    private final List<Conta> Contas;
    private final Context context;

    private ContaOnClickListener ContaOnClickListener;

    public ContasAdapter(Context context, List<Conta> Contas, ContaOnClickListener ContaOnClickListener) {
        this.context = context;
        this.Contas = Contas;
        this.ContaOnClickListener = ContaOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.Contas != null ? this.Contas.size() : 0;
    }

    @Override
    public ContasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_conta, viewGroup, false);

        // Cria o ViewHolder
        ContasViewHolder holder = new ContasViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ContasViewHolder holder, final int position) {
        // Atualiza a view
        Conta c = Contas.get(position);
        Log.d(TAG, "Conta no Adapter da RecyclerView: " + c.toString());

        Log.d(TAG, c.toString());

        holder.tNome.setText(c.nome +" "+ c.sobrenome);
        holder.progress.setVisibility(View.VISIBLE);
        if(c.urlFoto != null){
            holder.img.setImageURI(Uri.parse(c.urlFoto));
        }else{
            holder.img.setImageResource(R.drawable.ic_menu_camera);
        }

        // Click
        if (ContaOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContaOnClickListener.onClickConta(holder.itemView, position); // A variável position é final
                }
            });
        }

        holder.progress.setVisibility(View.INVISIBLE);
    }

    public interface ContaOnClickListener {
        public void onClickConta(View view, int idx);
    }

    // ViewHolder com as views
    public static class ContasViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        ImageView img;
        ProgressBar progress;

        public ContasViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.textview_card_adapterconta);
            img = (ImageView) view.findViewById(R.id.imageview_adapterconta);
            progress = (ProgressBar) view.findViewById(R.id.progressBar_adapterconta);
        }
    }
}
