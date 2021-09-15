package com.example.application.views.aboutme;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;

@PageTitle("About Me")
@Route(value = "aboutMe", layout = MainLayout.class)
public class AboutMeView extends HorizontalLayout  {

    private TextField name;
    private Button sayThanks;

    public AboutMeView() {
        addClassName("about-me-view");
        name = new TextField("Your name");
        sayThanks = new Button("Clik me...");
        add(name, sayThanks);
        setVerticalComponentAlignment(Alignment.END, name, sayThanks);
        sayThanks.addClickListener(e -> {
            Notification.show("Thank you " + name.getValue());
        });

        H2 header =  new H2(
                "Dziękuję bardzo za udział w bootcamp.");
        Paragraph paragraph = new Paragraph(
                "\nPodziekowania dla Kodilla, że udało im się z kompletnie 'zielonej' osoby " +
                "\npo wielu miesiącach uzyskać osobę która chociaż trochę wie jak się programuje." +
                        "\n przepraszam że z frontend praktycznie nie działa, ale zaczołem dzisiaj o godznie 18:00" +
                        "\n wiec.... to nie było wiele czasu na jego wykonanie." +
                        "\npo przerwie (na pewno conajmniej kilku dniowej, podejmę koleje próby dopracowania projektu" +
                        "\nnie będę wynieniał czego prakuje, bo to była by dość długa lista. " +
                        "\nDlatego skupię się na tym że osoba praktycznie bez doświadczenia (czyt. ja) napisała" +
                        "\naplikację w któej działają usługid, zapisuje dane do bazy/usuwa/modyfikuje/ i ma grontend" +
                        "\nktóry potafi pobrać dane z bazy i wyświetlić w frmie tabeli, jak na mnie to naprawdę sporo. " +
                        "\n\n");
        Paragraph signature = new Paragraph("Adam Piętka");

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout verticalLayoutMainContent = new VerticalLayout();
        verticalLayoutMainContent.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        verticalLayoutMainContent.add(header, paragraph, signature);
        add(verticalLayoutMainContent);
    }
}
