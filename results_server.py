import socket
import sys

HOST, PORT, FILE_NAME = '', 5000, sys.argv[1]             

tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp.bind((HOST, PORT))
tcp.listen(1)

while True:
    con, cliente = tcp.accept()
    print('Conected with {0}'.format(cliente))
    while True:
        msg = con.recv(15000)
        if not msg: break
        print msg
        f = open(FILE_NAME, "a")
        f.write(msg)
        f.close()
    print('Conection with client {0} finished'.format(cliente))
    con.close()
