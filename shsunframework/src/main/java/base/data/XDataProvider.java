package base.data;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shsun on 17/2/25.
 */

public class XDataProvider<T> {

    private List<T> mList;

    public XDataProvider(List<T> list){
        this.mList = list;
    }

    public int length() {
        return this.mList.size();
    }

    /**
     *     Appends an item to the end of the data provider.
     * @param item
     */
    public void addItem(T item){
        this.mList.add(item);
    }

    /**
     *     Adds a new item to the data provider at the specified index.
     * @param item
     * @param index
     */
    public void addItemAt(T item, int index){
        this.mList.add(index, item);
    }

    /**
     *     Appends multiple items to the end of the XDataProvider and dispatches a DataChangeType.ADD event.
     * @param items
     */
    public void addItems(List<T> items){
        this.mList.addAll(items);
    }

    /**
     *     Adds several items to the data provider at the specified index and dispatches a DataChangeType.ADD event.

     * @param items
     * @param index
     */
    public void addItemsAt(List<T> items, int index){
        this.mList.addAll(index, items);
    }

    /**
     *     Returns the item at the specified index.

     * @param index
     * @return
     */
    public T getItemAt(int index){return this.mList.get(index);}

    /**
     * Returns the index of the specified item.
     * @param item
     * @return
     */
    public int getItemIndex(T item){return this.mList.indexOf(item);}

    /**
     *     Invalidates all the data items that the XDataProvider contains and dispatches a DataChangeEvent.INVALIDATE_ALL event.
     */
    public void invalidate(){
        this.mList.clear();;
    }

    /**
     *     Invalidates the specified item.

     * @param item
     */
    public void invalidateItem(T item){
        this.mList.remove(item);
    }

    /**
     * Invalidates the item at the specified index.
     * @param index
     */
    public void invalidateItemAt(int index){
        this.mList.remove(index);
    }


//    merge(newData:Object):void
//    Appends the specified data into the data that the data provider contains and removes any duplicate items.



//    replaceItem(newItem:Object, oldItem:Object):Object
//    Replaces an existing item with a new item and dispatches a DataChangeType.REPLACE event.
//
//    replaceItemAt(newItem:Object, index:uint):Object
//    Replaces the item at the specified index and dispatches a DataChangeType.REPLACE event.
//
//    sort(... sortArgs):*
//    Sorts the items that the data provider contains and dispatches a DataChangeType.SORT event.
//
//    sortOn(fieldName:Object, options:Object = null):*
//    Sorts the items that the data provider contains by the specified field and dispatches a DataChangeType.SORT event.
//
//    toArray():Array
//    Creates an Array object representation of the data that the data provider contains.
//
//    toString():String
//    [override] Creates a string representation of the data that the data provider contains.

    public interface OnAdapterChangedListener<T> {

//        void beforeDataChange(ViewGroup parent, View view, T data, int position);
//        void afterDataChange(ViewGroup parent, View view, T data, int position);

        void onItemAdd(ViewGroup parent, View view, T data, int position);

        void onItemChanged(ViewGroup parent, View view, T data, int position);

        void onItemInvalidate(ViewGroup parent, View view, T data, int position);

        void onInvalidateAll(ViewGroup parent, View view, T data, int position);

        void onItemRemove(ViewGroup parent, View view, T data, int position);

        void onItemRemoveAll(ViewGroup parent, View view, T data, int position);

        void onItemReplace(ViewGroup parent, View view, T data, int position);

        void onItemSort(ViewGroup parent, View view, T data, int position);

    }

}
