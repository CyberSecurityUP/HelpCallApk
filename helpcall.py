import tkinter as tk
from tkinter import ttk
from twilio.rest import Client

def send_message_and_call():
# Insira suas credenciais da conta Twilio
    account_sid = ""
    auth_token = ""

    # Crie uma instância do cliente Twilio
    client = Client(account_sid, auth_token)

    # Defina o número de telefone de origem e destino
    from_phone_number = "+"  # Insira seu número de telefone Twilio
    to_phone_number = "+"    # Insira o número de telefone do destinatário

     # Obtenha a resposta da mensagem de socorro
    message_body = message_entry.get()

    # Enviar SMS com a mensagem de socorro
    message = client.messages.create(
        body=message_body,
        from_=from_phone_number,
        to=to_phone_number,
    )

    print(f"Mensagem enviada com sucesso para {to_phone_number}. SID da mensagem: {message.sid}")

    # Fazer uma chamada telefônica e tocar uma mensagem de socorro
    call = client.calls.create(
        twiml=f"<Response><Say voice='alice' language='pt-BR'>{message_body}</Say></Response>",
        from_=from_phone_number,
        to=to_phone_number,
    )

    print(f"Chamada realizada com sucesso para {to_phone_number}. SID da chamada: {call.sid}")


# Crie a janela principal do aplicativo
app = tk.Tk()
app.title("HelpCall by Joas")
app.geometry("400x200")

# Crie um rótulo e um campo de entrada para a mensagem de socorro
message_label = ttk.Label(app, text="Mensagem de Socorro:")
message_label.pack(padx=20, pady=10)

message_entry = ttk.Entry(app, width=50)
message_entry.pack(padx=20, pady=10)

# Crie um botão para enviar a mensagem de socorro e fazer a chamada
send_and_call_button = ttk.Button(app, text="Enviar Mensagem e Ligar", command=send_message_and_call)
send_and_call_button.pack(pady=20)

# Inicie o loop principal do aplicativo
app.mainloop()

# Fazer uma chamada telefônica e tocar uma mensagem de socorro
# Insira a URL do seu arquivo XML TwiML que toca a mensagem de socorro (hospedado em um servidor público)
#url_twiml = "URL"

#call = client.calls.create(
 #   twiml=f"<Response><Say>{message_body}</Say></Response>",
  #  from_=from_phone_number,
   # to=to_phone_number,
#)

#print(f"Chamada realizada com sucesso para {to_phone_number}. SID da chamada: {call.sid}")
