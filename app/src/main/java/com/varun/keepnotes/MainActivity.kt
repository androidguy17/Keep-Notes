package com.varun.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listNotes.add(notes(1,"Meet the professor", "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
        listNotes.add(notes(2,"To do work", "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
        listNotes.add(notes(3,"Pay fees", "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))

        var adapter:NotesAdapter = NotesAdapter(listNotes)

        listView.adapter=adapter
        var toast= Toast.makeText(this,"welcome user",Toast.LENGTH_LONG)
        toast.show()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

         menuInflater.inflate(R.menu.notes_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.addNote ->{
                var intent = Intent(this,addnotes::class.java)
                startActivity(intent)

            }
        }

        return super.onOptionsItemSelected(item)
    }





    inner class NotesAdapter:BaseAdapter{

        var list = ArrayList<notes>()

        constructor(list:ArrayList<notes>):super(){
            this.list=list
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var note = list[p0]
            var View = layoutInflater.inflate(R.layout.ticket,null)
            View.title.text= note.title
            View.tvdes.text = note.des
            return View

        }

        override fun getItem(p0: Int): Any {
            return list[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
           return list.size
        }







    }


}
