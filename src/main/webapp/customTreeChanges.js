//********* Custom functions *********/

function loadTree(){
	var xhr = new XMLHttpRequest();
	xhr.open('GET', "http://localhost:8080/fetchAll", true);
	//xhr.open('GET', "family_tree.json", true);
	xhr.onload = function() {
		if(this.status == 200){
			var jsonResponse = this.responseText;
			console.log("loadTree - " + jsonResponse);
			var members = JSON.parse(jsonResponse);
			family.load(members.members);
			family.fit();
		}
	};
	xhr.send();
}

function saveChanges(objToSave) {
	console.log("Before saveChanges - " + objToSave);
	var xhr = new XMLHttpRequest();
	xhr.open('PUT', "http://localhost:8080/save", true);
	xhr.setRequestHeader("Accept", "application/json");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onload = function() {
		if(this.status == 200){
			console.log("After saveChanges - " + this.responseText);
			var jsonResponse = this.responseText;
			var members = JSON.parse(jsonResponse);
			family.load(members.members);
			family.fit();
		}
	};
	// new member
	if((objToSave.id.toString()).includes("_")) {
		objToSave.id = "";
	}
	xhr.send(JSON.stringify(objToSave));
}

function deleteMember(memberID) {
	console.log("Before deleteMember - " + memberID);
	// new member - delete from server only its already saved and has a valid id
	if((memberID.toString()).includes("_")) {
		return;
	}
	
	var xhr = new XMLHttpRequest();
	xhr.open('DELETE', "http://localhost:8080/delete", true);
	xhr.setRequestHeader("Accept", "application/json");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onload = function() {
		if(this.status == 200){
			console.log("After delete - " + this.responseText);
			var jsonResponse = this.responseText;
			var members = JSON.parse(jsonResponse);
			family.load(members.members);
			family.fit();
		}
	};
	xhr.send('{"id":"' + memberID + '"}');
}

//********* FamilyTree Code *********/
//JavaScript
var family = new FamilyTree(document.getElementById('tree'), {
    mouseScrool: FamilyTree.action.none,
    mode: "dark",
    scaleInitial: FamilyTree.match.boundary,
    scaleMax: 1,
    nodeTreeMenu: true,
    nodeBinding: {
        field_0: "name",
        field_1: "birthDate",
        img_0: "imgPath"
    },
    nodeMenu: {
        details: { text: "Details" },
        edit: { text: "Edit" }
    },
    editForm: {
        titleBinding: "name",
        photoBinding: "imgPath",
        /*generateElementsFromFields: false,
        addMore: 'Add more elements',
        addMoreBtn: 'Add element',
        addMoreFieldName: 'Element name',*/
        elements: [
            { type: 'textbox', label: 'Full Name', binding: 'name' },

            [
                { type: 'date', label: 'Birth Date', binding: 'birthDate' },
                { type: 'date', label: 'Death Date', binding: 'deathDate' }
            ],
            { type: 'textbox', label: 'Address', binding: 'address' },
            [
                { type: 'select'
                , options: [{ value: 'india', text: 'India' }, { value: 'usa', text: 'USA' }, { value: 'china', text: 'China' }]
                , label: 'Country'
                , binding: 'country' },
                { type: 'textbox'
                , options: [{ value: 'raipur', text: 'Raipur' }, { value: 'raigarh', text: 'Raigarh' }, { value: 'indore', text: 'Indore' }]
                , label: 'City'
                , binding: 'city' },
            ],
            { type: 'textbox', label: 'Photo', binding: 'imgPath', btn: 'Upload' },
            { type: 'textbox', label: 'Phone', binding: 'phone' },
            { type: 'textbox', label: 'Email', binding: 'email' },
            { type: 'textbox', label: 'Occupation', binding: 'occupation' },
            { type: 'textbox'
            , options: [{ value: 'male', text: 'Male' }, { value: 'female', text: 'Female' }]
            , label: 'Gender', binding: 'gender' },
            { type: 'textbox', label: 'Notes', binding: 'notes' }
        ],
        buttons: {
            edit: {
                icon: FamilyTree.icon.edit(24, 24, '#fff'),
                text: 'Edit',
                hideIfEditMode: true,
                hideIfDetailsMode: false
            },
            share: {
                icon: FamilyTree.icon.share(24, 24, '#fff'),
                text: 'Share'
            },
            pdf: {
                icon: FamilyTree.icon.pdf(24, 24, '#fff'),
                text: 'Save as PDF'
            },

        }
    }
});

loadTree();
