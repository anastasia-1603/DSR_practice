function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var userApi = Vue.resource('/users{/id}');

Vue.component('user-form', {
    props: ['users', 'userAttr'],
    data: function () {
        return {
            id: '',
            surname: '',
            name: '',
            patronymic: '',
            email: ''
        }
    },
    watch: {
        userAttr: function (newValue) {
            this.id = newValue.id;
            this.surname = newValue.surname;
            this.name = newValue.name;
            this.patronymic = newValue.patronymic;
            this.email = newValue.email;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Write surname" v-model="surname"/>' +
        '<input type="text" placeholder="Write name" v-model="name"/>' +
        '<input type="text" placeholder="Write patronymic" v-model="patronymic"/>' +
        '<input type="text" placeholder="Write email" v-model="email"/>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var user = {
                id: this.id,
                surname: this.surname,
                name: this.name,
                patronymic: this.patronymic,
                email: this.email
            };
            if (this.id) {
                userApi.update(user)
                    .then(result =>
                        result.json().then(data => {
                            var index = getIndex(this.users, data.id);
                            this.users.splice(index, 5, data);
                            this.name = '';
                            this.surname = '';
                            this.patronymic = '';
                            this.email = '';
                            this.id = '';
                        }))
            } else {
                userApi.save({}, user).then(result =>
                    result.json().then(data => {
                        this.users.push(data);
                        this.name = '';
                        this.surname = '';
                        this.patronymic = '';
                        this.email = '';
                    })
                )
            }
        },
    }
})

Vue.component('users-row', {
    props: ['user', 'editUser', 'users'],
    template: '<tr>' +
        '<td>{{user.surname}}</td>' +
        '<td>{{user.name}}</td>' +
        '<td>{{user.patronymic}}</td>' +
        '<td>{{user.email}}</td>' +
        '<td><input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="delete" @click="del" /></td>' +
        '</tr>',
    methods: {
        edit: function () {
            this.editUser(this.user)
        },
        del: function() {
            userApi.remove({id: this.user.id}).then(result => {
                if (result.ok) {
                    this.users.splice(this.users.indexOf(this.user), 1)
                }
            })
        }
    }
})

Vue.component('users-list', {
    props: ['users'],
    data: function () {
        return {
            user: null
        }
    },
    template:
        '<div>' +
        '<user-form :users="users" :userAttr="user"/>' +
        '<table class="table">' +
        '<thead>' +
        '<tr>' +
        '<th scope="col">Surname</th>' +
        '<th scope="col">Name</th>' +
        '<th scope="col">Patronymic</th>' +
        '<th scope="col">Email</th>' +
        '<th scope="col"></th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<users-row v-for="user in users" ' +
        ':user="user" ' +
        ':key="user.id" ' +
        ':editUser="editUser" ' +
        ':users="users"/>' +
        '</tbody>' +
        '</table>' +
        '</div>',
    created: function () {
        userApi.get().then(result =>
            result.json().then(data =>
                data.forEach(user =>
                    this.users.push(user))
            )
        )
    },
    methods: {
        editUser: function (user) {
            this.user = user;
        }
    }
});

var app = new Vue({
    el: '#user',
    template: '<users-list :users="users"/>',
    data: {
        users: []
    }
});