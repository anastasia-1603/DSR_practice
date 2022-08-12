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
        userAttr: function (newValue, oldValue) {
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
                userApi.update({id: this.id}, user)
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
        }
    }
})

Vue.component('users-row', {
    props: ['user', 'editUser', 'users'],
    template: '<div>' +
        '<p>{{user.surname}} {{user.name}} {{user.patronymic}}</p> {{user.email}}' +
        '<span>' +
        '<p><input type="button" value="Edit" @click="edit" /></p>' +
        '<p><input type="button" value="X" @click="del" /></p>' +
        '</span>' +
        '<hr>' +
        '</div>',
    methods: {
        edit: function () {
            this.editUser(this.user)
        },
        del: function() {
            userApi.remove({id: this.user.id}).then(result => {
                if (result.ok) {
                    this.messages.splice(this.messages.indexOf(this.user), 1)
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
    template: '<div>' +
        '<user-form :users="users" :userAttr="user"/>' +
        '<div><users-row v-for="user in users" :user="user" :editUser="editUser" :users="users"/></div>' +
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
    el: '#app',
    template: '<users-list :users="users"/>',
    data: {
        users: []
    }
});