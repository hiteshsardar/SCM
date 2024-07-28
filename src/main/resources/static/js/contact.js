// base URl
const baseURL = "http://localhost:8089";

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};


const viewContactModal = document.getElementById('view_contact_modal');
// instance options object
const viewContactOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModalView = new Modal(viewContactModal, options, viewContactOptions);

function openViewContactModal() {
    contactModalView.show();
}

function hideViewContactModal() {
    contactModalView.hide();
}

async function loadContactData(id) {
    console.log(id);
    try {
        const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();
        document.querySelector('#contact_name').innerHTML = data.name;
        document.querySelector('#contact-email').innerHTML = data.email;
        document.querySelector('#contact_image').src = data.picture;
        document.querySelector('#contact_address').innerHTML = data.address;
        document.querySelector('#contact_phone').innerHTML = data.phoneNumber;
        document.querySelector('#contact_about').innerHTML = data.description;
        const contactFavorite = document.querySelector('#contact_favorite');
        if(data.favorite){
            contactFavorite.innerHTML = "<i class='fa-solid fa-star text-yellow-500'></i><i class='fa-solid fa-star text-yellow-500'></i><i class='fa-solid fa-star text-yellow-500'></i><i class='fa-solid fa-star text-yellow-500'></i><i class='fa-solid fa-star text-yellow-500'></i>";
        } else{
            contactFavorite.innerHTML = "<i class='fa-solid fa-star text-yellow-500'></i>"
        }
        document.querySelector('#contact_website').href = data.websiteLink;
        document.querySelector('#contact_website').innerHTML = data.websiteLink;
        document.querySelector('#contact_linkedIn').href = data.linkedInLink;
        document.querySelector('#contact_linkedIn').innerHTML = data.linkedInLink;

        openViewContactModal();
    } catch (error) {
        console.log(error);
    }

}

const deleteContactModal = document.getElementById('delete_contact_modal');
// instance options object
const deleteContactOptions = {
  id: 'delete_contact_modal',
  override: true
};

const contactModalDelete = new Modal(deleteContactModal, options, deleteContactOptions);

function openDeleteContactModal(contactId) {
    document.querySelector('#delete_contact').href = `/user/contacts/delete/${contactId}`;
    contactModalDelete.show();
}

function hideDeleteContactModal() {
    contactModalDelete.hide();
}