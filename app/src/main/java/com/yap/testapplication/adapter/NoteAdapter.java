package com.yap.testapplication.adapter;

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.yap.testapplication.R;
//import com.yap.testapplication.modal.Note;
//
//import java.text.DateFormat;
//
//import io.realm.Realm;
//import io.realm.RealmResults;
//
//public  class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
//
//    Context context;
//    RealmResults<Note> noteList;
//
//    public NoteAdapter(Context context, RealmResults<Note> noteList) {
//        this.context = context;
//        this.noteList = noteList;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Note note = noteList.get(position);
//        holder.title.setText(note.getTitle());
//        holder.descriptionOutput.setText(note.getDescription());
//
//        String formatTime = DateFormat.getDateTimeInstance().format(note.getCreatdTime());
//        holder.timeOutput.setText(formatTime);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu menu = new PopupMenu(context,v);
//                menu.getMenu().add("Delete");
//                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        if(item.getTitle().equals("Delete")){
//                            //delete the note
//                            Realm realm = Realm.getDefaultInstance();
//                            realm.beginTransaction();
//                            note.deleteFromRealm();
//                            realm.commitTransaction();
//                            Toast.makeText(context,"Note delete",Toast.LENGTH_SHORT).show();
//                        }
//                        return true;
//                    }
//                });
//                menu.show();
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return noteList.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder{
//
//       TextView title;
//       TextView descriptionOutput;
//       TextView timeOutput;
//
//       public MyViewHolder(@NonNull View itemView) {
//           super(itemView);
//
//           title = itemView.findViewById(R.id.title);
//           descriptionOutput = itemView.findViewById(R.id.descriptionOutput);
//           timeOutput = itemView.findViewById(R.id.date);
//       }
//   }
//}
