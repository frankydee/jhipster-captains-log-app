import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IELR } from 'app/shared/model/elr.model';
import { ELRService } from './elr.service';

@Component({
    selector: 'jhi-elr-delete-dialog',
    templateUrl: './elr-delete-dialog.component.html'
})
export class ELRDeleteDialogComponent {
    eLR: IELR;

    constructor(private eLRService: ELRService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eLRService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eLRListModification',
                content: 'Deleted an eLR'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-elr-delete-popup',
    template: ''
})
export class ELRDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eLR }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ELRDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.eLR = eLR;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
