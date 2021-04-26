import socket
import sys

HOST, PORT, FILE_NAME = '', 5000, sys.argv[1]             

tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp.bind((HOST, PORT))
tcp.listen(1)

while True:
    con, cliente = tcp.accept()
    print 'Concetado por', cliente
    while True:
        msg = con.recv(15000)
        if not msg: break
        print cliente, msg
        f = open(FILE_NAME, "a")
        f.write(msg)
        f.close()
    print 'Finalizando conexao do cliente', cliente
    con.close()
