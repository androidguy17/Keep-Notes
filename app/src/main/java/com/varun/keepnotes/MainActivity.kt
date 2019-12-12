package com.varun.keepnotes

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // listNotes.add(notes(1,"Meet the professor", "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
       // listNotes.add(notes(2,"To do work", "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))
        //listNotes.add(notes(3,"Pay fees", "In publishing and graphic design, lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content"))


        var toast= Toast.makeText(this,"welcome user",Toast.LENGTH_LONG)
        toast.show()


        //Load from DB

        LoadQuery("%")


    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")


    }

    fun LoadQuery(title:String){

        var dbManager= DbManager(this)
        val projection = arrayOf("ID","Title","Description")

        val selectionArgs= arrayOf(title)
        val cursor=dbManager.Query(projection,"Title like ?",selectionArgs,"Title")
        listNotes.clear()
        if(cursor.moveToFirst()){


        do{
            val ID = cursor.getInt(cursor.getColumnIndex("ID"))
            val Title = cursor.getString(cursor.getColumnIndex("Title"))
            val Description = cursor.getString(cursor.getColumnIndex("Description"))

            listNotes.add(notes(ID,Title,Description))



        }while(cursor.moveToNext())
        }
        var adapter:NotesAdapter = NotesAdapter(this,listNotes)

        listView.adapter=adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

         menuInflater.inflate(R.menu.notes_menu,menu)

        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView // typecasted as search view
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager //typecasted as search manager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                LoadQuery("%"+p0+"%")
                Toast.makeText(applicationContext,p0,Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // todo: pata nahi
                return  false
            }


        })


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
            var context:Context?= null

        var list = ArrayList<notes>()

        constructor(context: Context,list:ArrayList<notes>):super(){
            this.list=list
            this.context=context
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var note = list[p0]
            var View = layoutInflater.inflate(R.layout.ticket,null)
            View.title.text= note.title
            View.tvdes.text = note.des
            View.ivdelete.setOnClickListener( android.view.View.OnClickListener {

                var dbManager= DbManager(this@MainActivity)
                val selectionArgs = arrayOf(note.id.toString())
                dbManager.Delete("ID=?",selectionArgs)
                LoadQuery("%")
            })

           View.ivEdit.setOnClickListener( android.view.View.OnClickListener {

                    var intent = Intent(context,addnotes::class.java)
               intent.putExtra("ID",note.id)
               intent.putExtra("name",note.title)
               intent.putExtra("des",note.des)
                startActivity(intent)


           })
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
