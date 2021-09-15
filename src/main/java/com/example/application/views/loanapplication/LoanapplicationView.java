package com.example.application.views.loanapplication;

import java.util.Optional;

import com.example.application.data.dto.CustomerDTO;
import com.example.application.data.entity.Customer;
import com.example.application.data.service.RestClientService;
import com.example.application.data.service.SamplePersonService;

import com.example.application.views.customers.CustomersView;
import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;

@PageTitle("Loan application")
@Route(value = "LoanApp/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class LoanapplicationView extends Div implements BeforeEnterObserver {


    private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "customers/%d/edit";
    private final TextField filtrByAppId = new TextField();
    private final TextField filtrByCustomerId = new TextField();
    private final TextField filtrByLoanId = new TextField();
    final Grid<JsonNode> postsGrid = new Grid<JsonNode>();
    final Button fetchCustomers = new Button("Fetch all Loan Applications");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private BeanValidationBinder<Customer> binder;
    private Customer customer;
    private CustomerDTO customerDTO;
    private SamplePersonService samplePersonService;

    public LoanapplicationView(@Autowired RestClientService service) {
        this.samplePersonService = samplePersonService;
        addClassNames("customers-view", "flex", "flex-col", "h-full");
        // Create UI
        // TOP filter area
        HorizontalLayout topFieldsLoyot = new HorizontalLayout();
        topFieldsLoyot.setSpacing(true);
        topFieldsLoyot.setPadding(true);
        createTopArea(topFieldsLoyot);
        // button
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        buttonLayout.addClassName("button-layout");

        fetchCustomers.addClickListener( e -> postsGrid.setItems(service.getAllLoansApplications()));
        fetchCustomers.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(fetchCustomers);
        add(buttonLayout);

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);

        // Configure Grid
        postsGrid.addColumn(node -> node.get("id")).setHeader("Id").setTextAlign(ColumnTextAlign.END);
        postsGrid.addColumn(node -> node.get("customerId")).setHeader("cus. id");
        postsGrid.addColumn(node -> node.get("loansId")).setHeader("Loan id");
        postsGrid.addColumn(node -> node.get("incomeAmount")).setHeader("Income amount");
        postsGrid.addColumn(node -> node.get("employerName")).setHeader("Employer Name");
        postsGrid.addColumn(node -> node.get("employerPhoneNumber")).setHeader("Employer phone");
        postsGrid.addColumn(node -> node.get("otherLiabilities")).setHeader("Other liabilities");
        postsGrid.addColumn(node -> node.get("loanAmount")).setHeader("Loan amount");
        postsGrid.addColumn(node -> node.get("repaymentPeriodInMonth")).setHeader("Period");
        postsGrid.addColumn(node -> node.get("isApplicationAccepted")).setHeader("Is accepted");
        postsGrid.addColumn(node -> node.get("dateOfPayout")).setHeader("Date Of payout");

        postsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        postsGrid.setHeightFull();

        // when a row is selected or deselected, populate form
        postsGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
//                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().get());
            } else {
                clearForm();
                UI.getCurrent().navigate(CustomersView.class);
            }
        });

        // Configure Form
        // Bind fields. This where you'd define e.g. validation rules
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.customer == null) {
                    this.customer = new Customer();
                }
                binder.writeBean(this.customer);

                samplePersonService.update(this.customer);
                clearForm();
                refreshGrid();
                Notification.show("SamplePerson details stored.");
                UI.getCurrent().navigate(CustomersView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the samplePerson details.");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> samplePersonId = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (samplePersonId.isPresent()) {
            Optional<Customer> samplePersonFromBackend = samplePersonService.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent()) {
                populateForm(samplePersonFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested samplePerson was not found, ID = %d", samplePersonId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(CustomersView.class);
            }
        }
    }

    private void createTopArea( HorizontalLayout horizontalLayout){
        filtrByAppId.setPlaceholder("Filter by cust id");
        filtrByAppId.setClearButtonVisible(true);
        filtrByAppId.setValueChangeMode(ValueChangeMode.EAGER);
//        filtrByName.addValueChangeListener(e -> update());

        filtrByCustomerId.setPlaceholder("Filter by customer ID");
        filtrByCustomerId.setClearButtonVisible(true);
        filtrByCustomerId.setValueChangeMode(ValueChangeMode.EAGER);

        filtrByLoanId.setPlaceholder("Filter by loan ID");
        filtrByLoanId.setClearButtonVisible(true);
        filtrByLoanId.setValueChangeMode(ValueChangeMode.EAGER);

        horizontalLayout.add(filtrByAppId, filtrByCustomerId, filtrByLoanId);
        add(horizontalLayout);

    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("Email");
        TextField phone = new TextField("Phone");
        DatePicker dateOfBirth = new DatePicker("Date Of Birth");
        TextField occupation = new TextField("Occupation");
        Checkbox important = new Checkbox("Important");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{firstName, lastName, email, phone, dateOfBirth, occupation, important};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(postsGrid);
    }

    private void refreshGrid() {
        postsGrid.select(null);
        postsGrid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Customer value) {
        this.customer = value;
        binder.readBean(this.customer);

    }
}
