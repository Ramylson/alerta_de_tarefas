package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/*@author Ramylson A. Costa*/

public class ListaAudios extends AbstractListModel implements ComboBoxModel{
    
    private List<Audio> listaAudios;
    private Audio audioSelecinado;
    
    public ListaAudios(){
        this.listaAudios = new ArrayList<>();
    }
    
    @Override
    public int getSize() {
        return listaAudios.size();
    }

    @Override
    public Object getElementAt(int i) {
        return this.listaAudios.get(i);
    }

    @Override
    public void setSelectedItem(Object o) {
        if (o instanceof Audio){
            this.audioSelecinado = (Audio) o;
            fireContentsChanged(this.listaAudios, 0, this.listaAudios.size());
        }
    }

    @Override
    public Object getSelectedItem() {
        return this.audioSelecinado;
    }
    public void addAudio (Audio aAudio){
        this.listaAudios.add(aAudio);
    }
    public void reset(){
        this.listaAudios.clear();
    }
}