package com.kowshik.bluetooth;

import android.bluetooth.BluetoothSocket;

public class par_socket {
    private static BluetoothSocket socket;

    public static synchronized BluetoothSocket getSocket() {
        return socket;
    }

    public static synchronized void setSocket(BluetoothSocket socket) {
        par_socket.socket = socket;
    }
}
