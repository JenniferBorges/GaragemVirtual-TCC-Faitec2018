<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Registrar-se"/>
        </jsp:include>  
        <style>
            .modal-title {
                margin: 0 auto;
            }
            .modal-header .close{
                margin: 0;
                padding: 0;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="container my-5">
                <div class="row justify-content-center align-self-center">
                    <div class="col-lg-10 col-md-8">
                        <div class="card bg-secondary shadow border-0">
                            <div class="card-body px-lg-5 py-lg-5">
                                <div class="text-muted mb-4">
                                    <small>Preencha os campos abaixo para registrar-se</small>
                                </div>
                                <form method="POST">
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <div class="input-group input-group-alternative">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-user fa-fw"></i></span>
                                                </div>
                                                <input type="text" name="name" id="name" class="form-control form-control-alternative ${empty errors.name ? null : "is-invalid"}" placeholder="Nome" value="${user.name}" required/>                                                    
                                            </div>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <input type="text" id="lastName" name="lastName" class="form-control form-control-alternative ${empty errors.lastName ? null : "is-invalid"}" placeholder="Sobrenome" value="${user.lastName}" required/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fa fa-envelope fa-fw"></i>
                                                </span>
                                            </div>
                                            <input type="email" name="email" id="email" class="form-control form-control-alternative ${empty errors.email ? null : "is-invalid"}" placeholder="Email" value="${user.email}" required/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fa fa-key fa-fw"></i>
                                                </span>
                                            </div>
                                            <input type="password" name="password" id="password" class="form-control form-control-alternative ${empty errors.password ? null : "is-invalid"}" placeholder="Senha" required/>
                                        </div>
                                    </div>  
                                    <div class="text-muted font-italic mb-4"><small id="strength"></small></div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">
                                                    <i class="fa fa-key fa-fw"></i>
                                                </span>
                                            </div>
                                            <input type="password" name="confirmPassword" id="confirmPassword" class="form-control form-control-alternative ${empty errors.confirmPassword ? null : "is-invalid"}" placeholder="Confirme a senha" required/>
                                        </div>
                                    </div>
                                    <div class="row my-4">
                                        <div class="col-12">
                                            <div class="custom-control custom-control-alternative custom-checkbox">
                                                <input class="custom-control-input" id="customCheckRegister" type="checkbox" required>
                                                <label class="custom-control-label" for="customCheckRegister">
                                                    <span class="text-muted">Eu li e concordo com os </span>
                                                </label>
                                                <label style="font-size: .875rem;">
                                                    <a href="#" data-toggle="modal" data-target="#terms">
                                                        Termos de Uso
                                                    </a>
                                                    <div class="modal fade" id="terms" tabindex="-1" role="dialog" aria-labelledby="termsLabel" aria-hidden="true">
                                                        <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h2 class="modal-title" id="termsLabel">Termos de Uso</h2>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <p>Nós do <strong>Garagem Virtual</strong>, (a empresa) estamos entusiasmados em trazer a melhor experiência para os usuários que utilizam-se dos serviços fornecidos por nós.</p>
                                                                    <p>A nossa ação no entanto é neutra, ajudamos utilizadores a se encontrarem e se comprometerem em transações. A transação é entre vocês dois: a pessoa em busca de uma garagem (o motorista) e a pessoa que coloca a sua vaga a disposição (o dono da garagem).</p>
                                                                    <p>As garagens em questão não são de nossa propriedade e nem da responsabilidade da nossa empresa.</p>
                                                                    <p>Providenciamos a tecnologia necessária para que a tal possa acontecer. Ficamos felizes por você utilizar o <strong>Garagem Virtual</strong> (o "website").</p>
                                                                    <p>Estes termos que aqui se encontram, guiam você para usar e desfrutar do nosso site. Por favor leia com atenção para que possamos colaborar de maneira mais eficiente.</p>
                                                                    <ol>
                                                                        <li><strong>O básico dos termos de uso</strong>
                                                                            <ol>
                                                                                <li><p>Se você se registrar ou usar os nossos serviços, significa que aceita estes Termos de Uso ou qualquer outro regulamento que nos atualizemos ao longo do tempo.</p></li>
                                                                                <li><p>Estes Termos de Uso são aplicavéis a todos os usuários do serviço que providênciamos, independentemente de como acessa ou usa o serviço, a través da internet, do seu dispositivo móvel ou de qualquer outro método.</p></li>
                                                                                <li><p>O não cumprimento dos Termos de Uso você estará sujeito a severas penas civis e criminais.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>A responsabilidade do Garagem Virtual</strong>
                                                                            <ol>
                                                                                <li><p>A nossa responsabilidade consiste em anunciar devidamente as garagens através do nossa pataforma online de acordo com a proposta de seu proprietário, sempre que esta última tenha sido aprovada.</p></li>
                                                                                <li><p>Fica fora da responsabilidade do <strong>Garagem Virtual</strong>, os danos causados pelos motoristas às instalações das vagas de estacionamentos ou qualquer tipo de degradação ligado as suas ações. O motorista é exclusivamente responsável pelos danos causados. Lembre-se que a boa conduta deve ser de rigor e da responsabilidade do mesmo motorista.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual</strong> não é responsável por qualquer tipo de dano, vicio ou outros defeitos ligados a prestação de serviço de estacionamento na vaga reservada. Todos os danos causados a terceiros também são da responsabilidade do motorista.</p></li>
                                                                                <li><p>Nenhuma informação do usuário irá ser divulgada a não ser que a permissão tenha sido devidamente concedida</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li>
                                                                            <strong>A responsabilidade a cargo do dono da garagem</strong>
                                                                            <ol>
                                                                                <li><p>Somente os indivíduos que sejam donos com pleno direito de uso sobre a garagem podem anuncia-la. Não se autoriza a promulgação de garagens por pessoas que não tenham titularidade sobre a tal. Estes mesmos podem ser sujeitos a penas judiciais.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual</strong> não é responsável por situações derivadas ao uso ilegitimo decadastro de vagas e subsequentes repercusões.</p></li>
                                                                                <li><p>Caso existe dúvidas relativas ao uso legitimo da vaga por parte de um usuário, o <strong>Garagem Virtual</strong> poderá solicitar documentação para comprovar a devida propriedade.Caso o dono da vaga não forneça os documentos, será excluído da comunidade sem no entanto ser responsável de tomar ações jurídicas.</p></li>
                                                                                <li><p>Os dono da garagem têm por obrigação de garantir a disponibilidade das vagas reservadas no nosso Site. Caso contrario estarão sujeitos a que os anúncios sejam suspensos num primeiro tempo e logo excluídos da comunidade de usuários.</p></li>
                                                                                <li><p>Os dono da garagem deve por ao dispor dos usuários as informações e os recursos necesarios para que os motoristas consigam usufruir plenamente da vaga.</p></li>
                                                                                <li><p>É-lhes exigido o dever de providenciar a qualidade dos serviços tal como fora manunciados na descrição do anúncio.</p></li>
                                                                                <li><p>É-lhe exigido que atualizem as informações relativas ao cadastro da vaga.</p></li>
                                                                                <li><p>É-lhes exigido que não dêem a confundir as suas atividades com a atividades do <strong>Garagem Virtual</strong> através de qualquer manobra.</p></li>
                                                                                <li><p>Não é permitido promover qualquer tipo de informação, fazendo-se passar pelo <strong>Garagem Virtual</strong> sob pena de ser suspenso ou excluído caso não proceda as devidas alterações em menos de 24h.</p></li>
                                                                                <li><p>Conteúdos abusivos/ofensivos não podem constar na divulgação das vagas.</p></li>
                                                                                <li><p>De igual maneira, não é permitido usar os serviço do <strong>Garagem Virtual</strong> para promover entidades com os mesmos propósitos e atividades da nossa empresa. A mesmas penas de suspensão e exclusão serão então aplicadas.</p></li>
                                                                                <li><p>O dono da garagem é inteiramente responsável pela integridade das informações cadastradas e da sua adequação com a realidade que encontrará o motorista.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual</strong> não é responsável por nenhuma obrigação tributária decorrente dasatividades de estacionamento. Todas as obrigações econômicas relativas ao aluguel da vaga são exclusivamente ao cargo do dono da vaga.</p></li>
                                                                                <li><p>O dono da garagem têm por obrigação de fornecer as informações completas e necessárias para o motorista poder usufruir da vaga. Essas informações devem constar nos campos disponivéis durante o cadastro da vaga. O <strong>Garagem Virtual</strong> não é responsável pela falta de informação providenciada.</p></li>
                                                                                <li><p>Comoreferido no punto 3.6, o dono da garagem é responsavel pela atualização das informações de cadastro. Sempre que uma modificação suceda (mudança de referências bancárias, de email ou de propriedade da vaga, caso seja cedida a outro individuo), devem ser atualizadas no cadastro.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li>
                                                                            <strong>A responsabilidade a cargo da demanda - ou suário motorista</strong>
                                                                            <ol>
                                                                                <li><p>O motorista deve reservar a sua vaga com antecedência e o pagamento deve ser efetuado simultaneamente com a reserva. O <strong>Garagem Virtual</strong> não se responsabiliza pela falta de pagamento da dita obrigação acordada.</p></li>
                                                                                <li><p>Ao usar o Site e serviços do <strong>Garagem Virtual</strong>, o motorista automaticamente declara estar de acordo com os riscos relativos as transações. Requere-se dos motoristas que se informem sobre os Termos de Uso e serviço. É da responsabilidade dos motoristas que estejem informados dos termos decumprimento da reserva - horários de disponibilidade da vaga, custosadicionais.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual</strong> não é responsável da prestação de serviços entre dono da garagem e o motorista.</p></li>
                                                                                <li><p>É da responsabilidade do motorista informar o <strong>Garagem Virtual</strong> do não cumprimento do acordo por parte do dono da garagem num prazo não excedendo 24h.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>O serviço providenciado pelo Garagem Virtual</strong>
                                                                            <ol>
                                                                                <li><p>As duas partes, nomeadamente: o motorista e o dono da garagem devem usar o nosso site em conformidade com a lei e as suas capacidades jurídicas e legais. O dono da garagem que cadastra sua vaga será responsável por ela, e pela sua representação júridica.</p></li>
                                                                                <li><p>Ficam proibidas de usar os serviços do <strong>Garagem Virtual</strong>, toda pessoa que não tenha capacidade legal para o fazer, interditados, menores de idade. De igual maneira ficará proibido o acesso as pessoas cuja conta tenha sido excluída pelos motivos discutidos aqui nos Termos de Uso de serviço.</p></li>
                                                                                <li><p>É requerido que todos os usuários da plataforma se cadastrem no <strong>Garagem Virtual</strong>. E que informem todos os dados requisitados pelos formulários durante o cadastro. Os usúarios são plenamente responsáveis pelas informações que fornecem.</p></li>
                                                                                <li><p>Cada cadastro deve ser único garantindo a titularidade de um só individuo. Em caso de duplicidade, cabe o direito ao <strong>Garagem Virtual</strong> de excluir a conta do usuário.</p></li>
                                                                                <li><p>As informações incompletas ou erradas fornecidas pelo usuário, não são da responsabilidade do <strong>Garagem Virtual</strong>. O <strong>Garagem Virtual</strong> reserva-se no entanto o direito de cancelar a dita conta caso o usuário recuse fornecer os dados corretos.</p></li>
                                                                                <li><p>A submissão do cadastro acarreta como consequencia que todos os usuários estão deacordo com os Termos de Uso do serviço.</p></li>
                                                                                <li><p>Após o primeiro cadastro, sempre que um usuário quiser acessar a sua conta, ser-lhe-á requerido o email e senha informados. Somente essas informações lhe permitirão acesso.</p></li>
                                                                                <li><p>O uso correto do email e senha são da responsabilidade do usuário, bem como a sua divulgação a terceiros. O <strong>Garagem Virtual</strong> mantém-se ao dispor caso o usuário perca a sua senha ou suspeite de um uso não permitido da sua conta sempre que se poder identificar como legitimo titular da conta.</p></li>
                                                                                <li><p>Submentem-se a ser desativadas, todas as contas que forem suspeitas de ter sido vendidas,alugadas, cedidas a terceiros. Esses mesmos usuários não poderam se cadastrar novamente, ou tentar se cadastrar novamente através de outra conta sem o consentimento do <strong>Garagem Virtual</strong>.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual</strong> reserva-se o direito de exigir informações suplementares ao usuários para prosseguir com o serviço de aproximação.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>Anunciar uma vaga</strong>
                                                                            <ol>
                                                                                <li><p>Ao cadastrar a sua vaga, o dono da garagem deverá devidamente informar no anúncio todas as componentes permitindo o acesso ao usuário: tipo de vaga, preço pretendido,período, potencialmente acompanhado por imagens da vaga. Outros tipos de informações como presença de camera, cobertura e tipo de acesso.</p></li>
                                                                                <li><p>O período constitui uma parte importante da compreensão do serviço fornecido. Existem vagas por período e por evento. As vagas por período podem ser diárias, semanais ou mensais. O dono da garagem, terá de informar nos detalhes do estacionamento os horários durante os quais pretende alugar a vaga.</p></li>
                                                                                <li><p>Quando aprovado o anúncio, esse mesmo aparecerá publicado no devido espaço do Site. O <strong>Garagem Virtual</strong> reserva-se o direito de acompanhar com informações suplementares caso ache relevante</p></li>
                                                                                <li><p>Sempre que uma proposta de anúncio for negada, a cessassão de lucros não é da responsabilidade do  <strong>Garagem Virtual</strong>.</p></li>
                                                                                <li><p>Sempre que imagens forem usadas no anúncio, devem ter sido préviamente autorizadas pelo indivíduos donos da imagem. O <strong>Garagem Virtual</strong> não se responsabiliza pelo uso ilegitimo de imagens.</p></li>
                                                                                <li><p>Caso as condições para o motorista usufruir plenamente do espaço da vaga não forem reunidas. O <strong>Garagem Virtual</strong> reserva-se o direito de excluir o anúncio do site e até mesmo o próprio dono da garagem. No entanto não cabe ao <strong>Garagem Virtual </strong>tomar as iniciativas civis e penais contra o dono da garagem</p></li>
                                                                                <li><p>Dentro da nossa ambição de fornecer a melhor experiência de uso na nossa plataforma, o <strong>Garagem Virtual</strong> reserva-se o direito de dar a oportunidade aos motoristas que avaliem os donos das garagens mediante vários critérios tais como: a qualidade da vaga de estacionamente, a veracidade das informações colocadas no anúncio, a qualidade da prestação no seu global. Essa mesma avaliação poderá ser consultada pela comunidade de usuários e será utilizada para pontuar e rankear os donos das garagens. Esta mesma avaliação poderá ser feita pelo dono da garagem em relação ao motorista</p></li>
                                                                                <li><p>Sem aviso prévio e motivos de justificação, o <strong>Garagem Virtual</strong> reserva-se o direito de cancelar o anúncio e a conta do dono da garagem caso os retornos dos motoristas se revelem insatisfatorios.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>Exclusão de contas</strong>
                                                                            <ol>
                                                                                <li><p>Existem vários motivos levando a um cancelamento de conta. Notavelmente entre eles : o não cumprimento dos Termos de Uso, ou uma avaliação insatisfatoria dos serviços fornecidos pelo dono da vaga. Caso tal suceda, o montante da tranferência poderá ser integralmente retornado ao motorista para evitar que saia lesado do nãocumprimento. O dono da vaga deverá então se manter afastado das atividades do Site e não procurar em cadastrar-se de novo.</p></li>
                                                                                <li><p>No momento do cancelamento da conta, o dono da vaga deve-se comprometer as todas as reservas em aberto de acordo com as condições de Termo de Uso.</p></li>
                                                                                <li><p>Sempre que for a conta do motorista a cancelada, e que ainda tenha reservas ativas, o devido montante do pagamento lhe será devolvido.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>As modalidades de cancelamento</strong>
                                                                            <ol>
                                                                                <li><p>Os cancelamentos por parte dos motoristas são aceites até 24h antes do período de reserva solicitado.</p></li>
                                                                                <li><p>Os cancelamentos por parte dos donos das garagens são aceites até 72h antes do período de reserva. Neste preciso caso, o <strong>Garagem Virtual</strong> informará devidamente o motorista que receberá de volta a integralidade do montante.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual </strong>não é responsável pelos prejuízos derivados aos cancelamentos, ambas as partes são responsáveis por se entender caso tal situação suceda.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>Evoluções dos Termos de Uso</strong>
                                                                            <ol>
                                                                                <li><p>O Termos de Uso de Serviço pode ser alterado a qualquer momento, modificações relativas as comissões cobradas podem suceder entre outros aspetos. As alterações aconteceram 15 dias uteis depois de terem sido publicadas. Toda a comunidade de usuários tem como obrigação de se informar sobre as evoluções dos termos de uso. Os usuários podem sempre entrar em contato conosco para ter mais amplias informações. Note-se que passado os 15 dias, os novos termos de uso serão a referência que todos os utilizadores deverão seguir.</p></li>
                                                                                <li><p>Os ditos novos Termos serão aplicáveis aos anúncios referenciados depois dos 15 dias de vigência</p></li>
                                                                            </ol>
                                                                        </li>
                                                                        <li><strong>Disposições gerais.</strong>
                                                                            <ol>
                                                                                <li><p>Todos os usuários declaram estar em pleno conhecimento dos Termos de Uso sempre que recorrerem aos serviços do Parkingaki de maneira livre sem obrigações que os impeça de se conformarem aos termos.</p></li>
                                                                                <li><p>Por este meio o <strong>Garagem Virtual</strong> estabelece que poderá fazer valer a suas prerrogativas perante os seus cessionários, licenciados e sucessores.</p></li>
                                                                                <li><p>Os usuários declaram ter pleno conhecimento dos Termos e Uso como referido no ponto 11a, não podendo, sem nenhuma excepção, alegar como justificativa ou defesa, o desconhecimento, a mal interpretação, lapso ou esquecimento das normas que constam nestes Termos e Uso.</p></li>
                                                                                <li><p>Estes Termos de Uso substituem qualquer outro tipo acordo previamente estabelecido entre qualquer usuário. Os quais concordam em não subsistir, quanto a acordos prévios, quaisquer débitos.</p></li>
                                                                                <li><p>Os acordos passados entre motoristas e donos da vaga não são da responsabilidade do <strong>Garagem Virtual</strong> sempre que estes não estiver em fase com os Termos de Uso.</p></li>
                                                                                <li><p>O <strong>Garagem Virtual </strong>reserva-se o direito de agir contra os donos das vagas,especialmente, mas não se limitando a, ações decorrentes de indenizações à usuários amparadas pela lei civil ou consumeirista.</p></li>
                                                                                <li><p>Não se pode interpretar estes Termos de Uso como uma maneira de viabilizar uma sociedade, associação ou qualquer outra tipo de relação de natureza trabalhista entre a comunidade de Usuários.</p></li>
                                                                                <li><p>Fica eleito o foro desta Comarca de Santa Rita do Sapucaí como competente para dirimir quaisquer dúvidas ou questões decorrentes deste Termo, com expressa renúncia das Partes de qualquer outro, por mais privilegiado que seja.</p></li>
                                                                            </ol>
                                                                        </li>
                                                                    </ol>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <button id="register" class="btn btn-primary mt-4" name="register" type="submit">Registrar <i class="fa fa-save"></i></button>		
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
        <script language="javascript">
            document.getElementById("password").addEventListener("keyup", function () {
                passwordChanged();
            });

            function passwordChanged() {
                var strength = document.getElementById('strength');
                var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
                var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
                var enoughRegex = new RegExp("(?=.{6,}).*", "g");
                var pwd = document.getElementById("password");
                if (pwd.value.length === 0) {
                    strength.innerHTML = '';
                } else if (false === enoughRegex.test(pwd.value)) {
                    strength.innerHTML = 'Senha muito pequena';
                } else if (strongRegex.test(pwd.value)) {
                    strength.innerHTML = 'Nível da senha: <span class="text-success font-weight-700">Forte</span>';
                } else if (mediumRegex.test(pwd.value)) {
                    strength.innerHTML = 'Nível da senha: <span class="text-warning font-weight-700">Médio</span>';
                } else {
                    strength.innerHTML = 'Nível da senha: <span class="text-danger font-weight-700">Fraco</span>';
                }
            }
        </script>
    </body>
</html>
