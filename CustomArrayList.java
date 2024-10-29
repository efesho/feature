import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> extends AbstractList<E> {
    private E[] objs;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayList() {
        objs = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;

    }
    public CustomArrayList(int initialCapacity) {
        objs = (E[]) new Object[initialCapacity];
        size = 0;
    }

    private void updateCapacity(int minCapacity) {
        if (minCapacity > objs.length) {
            int newCapacity = objs.length*2;
            objs = Arrays.copyOf(objs, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public boolean add(E obj) {
        updateCapacity(size);
        objs[size++] = obj;
        return true;
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        updateCapacity(size + c.size());
        for(E element: c) {
            objs[size++] = element;
        }
        return true;
    }

    public int size() {
        return this.size;
    }

    public E remove(int index) {
        checkIndex(index);
        E removedObj = objs[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(objs, index + 1, objs, index, numMoved);
        }
        objs[--size] = null;
        return removedObj;

    }


    public boolean remove(Object o) {
        for (int i=0;i< objs.length; i++){
            if(objs[i] != null && objs[i].equals(o)){
                int numMoved = size - i - 1;
                if (numMoved > 0) {
                    System.arraycopy(objs, i+1, objs, i, numMoved);
                }
                objs[--size] = null;
                return true;
            }
        }
        return false;
    }

    public void clear() {
        Arrays.fill(objs, null);
        size = 0;

    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void sort(Comparator<? super E> c) {
        if (size < 2) {
            return;
        }
        E[] temp = (E[]) new Object[size];
        mergeSort(c, temp, 0, size - 1);
    }

    private void mergeSort(Comparator<? super E> c, E[] temp, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(c, temp, left, mid);
            mergeSort(c, temp, mid + 1, right);
            merge(c, temp, left, mid, right);
        }
    }

    private void merge(Comparator<? super E> c, E[] temp, int left, int mid, int right) {
        System.arraycopy(objs, left, temp, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (c.compare(temp[i], temp[j]) <= 0) {
                objs[k++] = temp[i++];
            } else {
                objs[k++] = temp[j++];
            }
        }
        while (i<=mid){
            objs[k++] = temp[i++];
        }
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (objs[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    public E get(int index) {
        checkIndex(index);
        return objs[index];
    }

    public String toString(){
        return Arrays.toString(Arrays.copyOf(objs, size));
    }
    public static void main(String[] args) {
        CustomArrayList<Integer> some = new CustomArrayList<>(3);
        some.add(1);
        some.add(2);
        some.add(3);
        some.sort(Comparator.naturalOrder());
        CustomArrayList<Integer> some1 = new CustomArrayList<>(3);
        some1.addAll(some);
       // int boy = some.get(4);
        System.out.println(some1);
    }
}
